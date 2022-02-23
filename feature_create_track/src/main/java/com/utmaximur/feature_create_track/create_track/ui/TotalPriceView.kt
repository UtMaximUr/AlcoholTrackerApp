package com.utmaximur.feature_create_track.create_track.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.utmaximur.feature_create_track.R
import com.utmaximur.feature_create_track.create_track.CreateTrackViewModel

@Composable
fun TotalPrice(viewModel: CreateTrackViewModel) {

    val totalMoneyState by viewModel.totalMoney.observeAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(text = stringResource(id = R.string.add_total_money))
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp),
                text = totalMoneyState.orEmpty(),
                textAlign = TextAlign.End
            )
            Text(text = stringResource(id = R.string.add_currency))
        }
    }
}