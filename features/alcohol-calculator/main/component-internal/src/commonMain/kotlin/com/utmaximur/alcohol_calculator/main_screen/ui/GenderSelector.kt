package com.utmaximur.alcohol_calculator.main_screen.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.alcohol_calculator.models.Gender
import com.utmaximur.design.text.TextOutlinedLabel
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.cd_female
import features.alcohol_calculator.main.cd_male
import features.alcohol_calculator.main.gender
import features.alcohol_calculator.main.ic_female_24dp
import features.alcohol_calculator.main.ic_male_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GenderSelector(
    selectedGender: Gender,
    onSelectGenderClick: (Gender) -> Unit,
) {
    TextOutlinedLabel(title = stringResource(Res.string.gender))
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        Gender.uiValues.forEachIndexed { index, gender ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = Gender.uiValues.size
                ),
                onClick = { onSelectGenderClick(Gender.valueOf(gender.name)) },
                selected = gender.name == selectedGender.name,
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.tertiary,
                    activeContentColor = Color.White,
                    inactiveContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(gender.icon),
                        contentDescription = stringResource(gender.description),
                    )
                },
                label = {
                    Text(
                        text = stringResource(gender.description),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            )
        }
    }
}

private val Gender.icon
    get() = when (this) {
        Gender.MALE -> Res.drawable.ic_male_24dp
        Gender.FEMALE -> Res.drawable.ic_female_24dp
        Gender.NONE -> error("Unsupported type")
    }

private val Gender.description
    get() = when (this) {
        Gender.MALE -> Res.string.cd_male
        Gender.FEMALE -> Res.string.cd_female
        Gender.NONE -> error("Unsupported type")
    }