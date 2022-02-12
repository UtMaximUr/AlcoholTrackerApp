package com.utmaximur.alcoholtracker.presentation.create_my_drink.ui


import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.alcoholtracker.domain.entity.Icon
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrinkViewModel
import com.utmaximur.alcoholtracker.util.getIdRaw

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrinkIcon(viewModel: CreateMyDrinkViewModel) {

    val icons = viewModel.getIcons()
    val selectedState = remember { mutableStateOf<Icon?>(null) }

    LazyRow(
        modifier = Modifier
            .fillMaxHeight(0.16f)
            .padding(16.dp)
    ) {
        items(icons.size) { index ->
            IconItem(
                icon = icons[index],
                selectedState = selectedState,
                onClick = {
                    selectedState.value = it
                    viewModel.onIconChange(it.icon)
                })
        }
    }
}

@Composable
fun IconItem(
    context: Context = LocalContext.current,
    icon: Icon,
    selectedState: MutableState<Icon?>,
    onClick: (Icon) -> Unit
) {
    GlideImage(
        imageModel = icon.icon.getIdRaw(context),
        modifier = Modifier
            .aspectRatio(1f)
            .alpha(if (selectedState.value == icon) 1f else 0.2f)
            .clickable {
                onClick(icon)
            }
    )
}