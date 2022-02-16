package com.utmaximur.alcoholtracker.presentation.create_my_drink.ui

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
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrinkViewModel
import com.utmaximur.alcoholtracker.util.format1f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrinkDegree(viewModel: CreateMyDrinkViewModel) {

    val sliderPosition = remember { mutableStateOf(25f..75f) }.apply {
        viewModel.degreeListState.value?.let { degree ->
            value = degree.first().toFloat()..degree.last().toFloat()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            Text(
                text = stringResource(id = R.string.dialog_create_drink_add_degree),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                fontSize = 16.sp
            )
            Text(
                text = sliderPosition.value.toRange().lower.format1f(),
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                fontSize = 16.sp
            )
            Text(
                text = stringResource(id = R.string.divide_value),
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                fontSize = 16.sp
            )
            Text(
                text = sliderPosition.value.toRange().upper.format1f(),
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                fontSize = 16.sp
            )
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