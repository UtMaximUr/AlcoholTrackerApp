package com.utmaximur.detailTrack.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.extensions.bottomFade
import com.utmaximur.design.extensions.fadingEdge
import com.utmaximur.design.topbar.TopBar
import com.utmaximur.design.ui.DateButtonGroup
import com.utmaximur.detailTrack.DetailTrackComponent
import com.utmaximur.domain.models.TrackData
import detailTrack.resources.Res
import detailTrack.resources.cd_delete
import detailTrack.resources.cd_save
import detailTrack.resources.ic_delete_button
import detailTrack.resources.ic_save_button
import detailTrack.resources.title_edit_track
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DetailTrackScreen(
    component: DetailTrackComponent,
    trackBuilder: TrackData.Builder,
) {
    val state by component.model.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = component::navigateBack,
                title = stringResource(Res.string.title_edit_track),
                actions = {
                    IconButton(onClick = component::onDeleteClick) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_delete_button),
                            contentDescription = stringResource(Res.string.cd_delete)
                        )
                    }
                    IconButton(onClick = { component.onSaveClick(trackBuilder.build()) }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_save_button),
                            contentDescription = stringResource(Res.string.cd_save)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .fadingEdge(bottomFade)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RequestWidget(
                    state = state.requestTrackUi,
                ) { track ->
                    TrackContent(
                        trackData = trackBuilder,
                        track = track,
                        price = state.price,
                        onCalculatorClick = component::openCalculatorDialog
                    )
                }
                component.geocoderComponent.Render(Modifier)
                DateButtonGroup(
                    selectedDate = state.selectedDate,
                    onSelectDateClick = component::openDatePickerDialog,
                    onTodayClick = component::onTodayClick
                )
                RequestWidget(
                    state = state.requestTrackUi
                ) { track ->
                    TotalPrice(
                        currency = state.currency,
                        totalPrice = track.totalPrice
                    )
                }
            }
        }
    )
}