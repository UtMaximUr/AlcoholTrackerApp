package com.utmaximur.detailTrack.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.ui.ElevatedCardApp
import detailTrack.resources.Res
import detailTrack.resources.add_total_money
import detailTrack.resources.add_total_money_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun TotalPrice(
    currency: String,
    totalPrice: Float
) {

    ElevatedCardApp {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.add_total_money_title),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = stringResource(Res.string.add_total_money, totalPrice, currency),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}