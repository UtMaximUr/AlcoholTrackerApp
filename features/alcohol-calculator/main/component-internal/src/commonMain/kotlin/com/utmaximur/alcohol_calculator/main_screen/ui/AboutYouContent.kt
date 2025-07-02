package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utmaximur.alcohol_calculator.models.Gender
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.ui.ElevatedCardApp
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.about_you
import features.alcohol_calculator.main.cm
import features.alcohol_calculator.main.height
import features.alcohol_calculator.main.ic_height_24dp
import features.alcohol_calculator.main.ic_weight_24dp
import features.alcohol_calculator.main.kg
import features.alcohol_calculator.main.weight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AboutYouContent(
    height: Int,
    weight: Int,
    gender: Gender,
    changeHeight: (String) -> Unit,
    changeWeight: (String) -> Unit,
    changeGender: (Gender) -> Unit,
) {
    ElevatedCardApp(
        modifier = Modifier.padding(horizontal = 12.dp),
        contentPaddingValues = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(Res.string.about_you),
            color = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        InnerShadowTextField(
            title = stringResource(Res.string.height),
            keyboardType = KeyboardType.Number,
            textValue = height,
            onValueChange = changeHeight,
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_height_24dp),
                    contentDescription = stringResource(Res.string.height),
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            },
            suffixText = stringResource(Res.string.cm),
        )
        InnerShadowTextField(
            title = stringResource(Res.string.weight),
            keyboardType = KeyboardType.Number,
            textValue = weight,
            onValueChange = changeWeight,
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_weight_24dp),
                    contentDescription = stringResource(Res.string.weight),
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            },
            suffixText = stringResource(Res.string.kg),
        )
        GenderSelector(
            selectedGender = gender,
            onSelectGenderClick = changeGender,
        )
    }
}