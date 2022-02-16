package com.utmaximur.alcoholtracker.presentation.dialog.track_list.ui


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.navigation.NavigationDestination
import com.utmaximur.alcoholtracker.presentation.dialog.track_list.TrackListViewModel
import com.utmaximur.alcoholtracker.presentation.main.ui.theme.DeleteColor
import com.utmaximur.alcoholtracker.presentation.main.ui.theme.EditColor
import com.utmaximur.alcoholtracker.util.SELECT_DATE
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrackListBottomDialogScreen(
    viewModel: TrackListViewModel = hiltViewModel(),
    navController: NavHostController,
    modalBottomSheetState: ModalBottomSheetState
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val scope = rememberCoroutineScope()
    val tracksByDay: List<Track> by viewModel.tracksByDay.observeAsState(listOf())

    if (modalBottomSheetState.isVisible) {
        val date = navController.currentBackStackEntry?.savedStateHandle?.get<Long>(SELECT_DATE)
        date?.let {
            viewModel.trackByDayChange(it)
        }
    } else {
        viewModel.clearTrackByDay()
    }

    Row {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_left_24dp),
                contentDescription = null,
                modifier = Modifier.padding(6.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_divider),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            alignment = Alignment.Center
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right_24dp),
                contentDescription = null,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
    LazyColumn(
        modifier = Modifier.heightIn(min = screenHeight / 2, max = screenHeight)
    ) {
        items(tracksByDay.size) { index ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToEnd) {
                        scope.launch {
                            modalBottomSheetState.hide()
                        }
                        navController.navigate(
                            NavigationDestination.AddTrackScreen.route.plus(
                                "/${tracksByDay[index].id}"
                            )
                        )
                    } else if (it == DismissValue.DismissedToStart) {
                        viewModel.onDeleteDrink(tracksByDay[index])
                    }
                    false
                }
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(0.25f) },
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> MaterialTheme.colors.primary
                            DismissValue.DismissedToEnd -> EditColor
                            DismissValue.DismissedToStart -> DeleteColor
                        }
                    )
                    val alignment = when (direction) {
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                    }
                    val icon = when (direction) {
                        DismissDirection.StartToEnd -> Icons.Default.Edit
                        DismissDirection.EndToStart -> Icons.Default.Delete
                    }
                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            modifier = Modifier.scale(scale)
                        )
                    }
                }) {
                if (index < tracksByDay.size) {
                    TrackItem(track = tracksByDay[index])
                }
            }
        }
    }
}