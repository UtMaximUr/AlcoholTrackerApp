package com.utmaximur.feature_create_drink.ui


import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.feature_create_drink.R
import com.utmaximur.feature_create_drink.CreateMyDrinkViewModel
import com.utmaximur.utils.getIdRaw
import com.utmaximur.domain.entity.Icon

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrinkIcon(viewModel: CreateMyDrinkViewModel) {

    val icons = viewModel.getIcons()
    val selectedState = remember { mutableStateOf<String?>(null) }.apply {
        viewModel.iconState.value?.let { icon ->
            value = icon
        }
    }

    LazyRow(modifier = Modifier.padding(top = 12.dp)) {
        items(icons.size) { index ->
            IconItem(
                icon = icons[index],
                selectedState = selectedState,
                onClick = {
                    selectedState.value = it.icon
                    viewModel.onIconChange(it.icon)
                })
        }
    }
}

@Composable
fun IconItem(
    context: Context = LocalContext.current,
    icon: Icon,
    selectedState: MutableState<String?>,
    onClick: (Icon) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    GlideImage(
        imageModel = icon.icon.getIdRaw(context),
        modifier = Modifier
            .size(45.dp)
            .alpha(if (selectedState.value == icon.icon) 1f else 0.2f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onClick(icon)
            },
        contentDescription = stringResource(id = R.string.cd_icon_drink)
    )
}