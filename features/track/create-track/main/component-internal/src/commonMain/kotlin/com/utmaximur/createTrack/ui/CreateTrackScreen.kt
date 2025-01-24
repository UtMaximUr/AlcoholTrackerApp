package com.utmaximur.createTrack.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.utmaximur.createTrack.CreateTrackComponent
import com.utmaximur.design.extensions.bottomFade
import com.utmaximur.design.extensions.bounceClick
import com.utmaximur.design.extensions.fadingEdge
import com.utmaximur.design.topbar.TopBar
import com.utmaximur.design.ui.DateButtonGroup
import com.utmaximur.domain.models.TrackData
import createTrack.resources.Res
import createTrack.resources.cd_save
import createTrack.resources.ic_save_button
import createTrack.resources.title_create_drink
import createTrack.resources.title_create_track
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun CreateTrackScreen(
    component: CreateTrackComponent
) {
    val state by component.model.collectAsState()
    val trackBuilder = remember { TrackData.Builder() }
    LaunchedEffect(state.selectedDate) {
        trackBuilder.setDate(state.selectedDate)
    }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = component::navigateBack,
                title = stringResource(Res.string.title_create_track),
                actions = {
                    TextButton(
                        modifier = Modifier.bounceClick(),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        onClick = component::navigateToCreateDrink
                    ) {
                        Text(
                            text = stringResource(Res.string.title_create_drink),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
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
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .fadingEdge(bottomFade),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                item {
                    CreateTrackContent(
                        trackData = trackBuilder,
                        requestDrinksUi = state.requestDrinksUi,
                        price = state.price,
                        currency = state.currency,
                        onCalculatorClick = component::openCalculatorDialog,
                        onCurrencyClick = component::openCurrencyDialog,
                        onDeleteClick = component::onDeleteClick
                    )
                }
                item {
                    DateButtonGroup(
                        selectedDate = state.selectedDate,
                        onSelectDateClick = component::openDatePickerDialog,
                        onTodayClick = component::onTodayClick
                    )
                }
            }
        }
    )
}