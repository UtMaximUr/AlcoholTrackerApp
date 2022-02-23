package com.utmaximur.feature_create_drink.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RangeSlider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.toRange
import com.utmaximur.feature_create_drink.R
import com.utmaximur.feature_create_drink.CreateMyDrinkViewModel
import com.utmaximur.utils.format1f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrinkDegree(viewModel: CreateMyDrinkViewModel) {

    val sliderPosition = remember { mutableStateOf(25f..75f) }.apply {
        viewModel.degreeListState.value?.let { degree ->
            value = degree.first().toFloat()..degree.last().toFloat()
        }
    }

    Column(modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)) {
        Row {
            TextField(
                text = stringResource(id = R.string.dialog_create_drink_add_degree),
                modifier = Modifier.weight(1f)
            )
            TextField(text = sliderPosition.value.toRange().lower.format1f())
            TextField(text = stringResource(id = R.string.divide_value))
            TextField(text = sliderPosition.value.toRange().upper.format1f())
        }
        RangeSlider(
            values = sliderPosition.value,
            valueRange = 0f..100f,
            onValueChange = {
                viewModel.onDegreeChange(
                    sliderPosition.value.toRange().lower,
                    sliderPosition.value.toRange().upper
                )
                sliderPosition.value = it
            })
    }
}

@Composable
fun TextField(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colors.onPrimary,
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        fontSize = 16.sp,
        modifier = modifier
    )
}