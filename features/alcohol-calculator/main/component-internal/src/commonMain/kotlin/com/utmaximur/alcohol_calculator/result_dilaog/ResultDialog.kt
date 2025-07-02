package com.utmaximur.alcohol_calculator.result_dilaog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.alcohol_calculator.models.CalculateResult
import com.utmaximur.alcohol_calculator.main_screen.ui.InfoContent
import com.utmaximur.design.extensions.bounceClick
import com.utmaximur.design.modal.ModalBottomSheetApp
import features.alcohol_calculator.main.Res
import features.alcohol_calculator.main.bac
import features.alcohol_calculator.main.elimination
import features.alcohol_calculator.main.proceed
import features.alcohol_calculator.main.warning
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ResultDialog(
    component: ResultDialogComponent,
    calculateResult: CalculateResult,
) {
    ModalBottomSheetApp(
        onDismissRequest = component::dismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(Res.string.bac, calculateResult.bac),
                style = MaterialTheme.typography.titleMedium,

                )
            Text(
                text = stringResource(
                    Res.string.elimination,
                    calculateResult.eliminationTimeHh,
                    calculateResult.eliminationTimeMm
                ),
                style = MaterialTheme.typography.titleMedium,
            )
            InfoContent(text = stringResource(Res.string.warning))
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick(),
                onClick = component::dismiss,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
            ) {
                Text(
                    text = stringResource(Res.string.proceed),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                )
            }
        }
    }
}