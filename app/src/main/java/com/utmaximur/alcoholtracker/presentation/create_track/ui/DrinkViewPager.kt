package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackViewModel
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun ViewPagerDrink(
    viewModel: CreateTrackViewModel
) {

    val pagerState: PagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val drinksList by viewModel.drinksList.observeAsState()

    viewModel.position.observeAsState().apply {
        value?.let { position ->
            scope.launch {
                pagerState.scrollToPage(position)
            }
        }
    }

    val lifeCycleState = LocalLifecycleOwner.current.lifecycle.observeAsSate()
    when (lifeCycleState.value) {
        Lifecycle.Event.ON_RESUME -> viewModel.updateDrinks()
        else -> {
            lifeCycleState.value
        }
    }

    Card(
        modifier = Modifier.padding(bottom = 12.dp),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            drinksList?.let { drinkList ->
                HorizontalPager(
                    count = drinkList.size + 1,
                    state = pagerState
                ) { index ->
                    if (index != drinksList?.size) {
                        ItemDrink(drinksList?.get(index))
                    } else {
                        ItemDrink(null)
                    }
                    viewModel.onViewPagerPositionChange(pagerState.currentPage)
                }
                DotsIndicator(
                    drinkList.size + 1,
                    pagerState
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun DotsIndicator(
    totalDots: Int,
    pagerState: PagerState
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(bottom = 12.dp)
    ) {

        items(totalDots) { index ->
            if (index == pagerState.currentPage) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dot_18dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_dot_default_18dp),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
fun Lifecycle.observeAsSate(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsSate.addObserver(observer)
        onDispose {
            this@observeAsSate.removeObserver(observer)
        }
    }
    return state
}