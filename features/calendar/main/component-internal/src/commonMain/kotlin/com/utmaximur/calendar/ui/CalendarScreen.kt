package com.utmaximur.calendar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calendar.resources.Res
import calendar.resources.cd_add_track
import calendar.resources.ic_add_fab
import com.utmaximur.calendar.CalendarComponent
import com.utmaximur.calendar.models.localized
import com.utmaximur.calendar.ui.calendar.CalendarViewLayout
import com.utmaximur.calendar.ui.calendar.rememberCalendarState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarScreen(
    modifier: Modifier,
    component: CalendarComponent
) {
    val state by component.model.collectAsState()
    val calendarState = rememberCalendarState()

//    LaunchedEffect(Unit) {
//        component.initialFromDate(calendarState.firstDate)
//        snapshotFlow { calendarState.firstDate }
//            .collect(component::handleFromDate)
//    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = calendarState.currentMonthYear.localized().uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = component::toggleView) {
                        Icon(
                            painter = painterResource(state.calendarView.icon),
                            contentDescription = stringResource(state.calendarView.title)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxSize()
            ) {
                CalendarViewLayout(
                    modifier = Modifier,
                    calendarState = calendarState,
                    calendarView = state.calendarView,
                    requestUi = state.requestTracksUi,
                    currency = state.currency,
                    onItemClick = component::onTrackClick,
                    changeView = component::toggleView
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                onClick = component::onCreateTrackClick
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_add_fab),
                    contentDescription = stringResource(Res.string.cd_add_track),
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}