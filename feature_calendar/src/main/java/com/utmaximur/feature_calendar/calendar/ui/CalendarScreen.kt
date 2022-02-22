package com.utmaximur.feature_calendar.calendar.ui


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.utmaximur.utils.setNavigationResult
import com.utmaximur.utils.SELECT_DATE
import com.utmaximur.utils.SELECT_DAY_ADD
import kotlinx.coroutines.launch
import java.util.*
import java.util.Calendar.getInstance


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarScreen(
    navController: NavHostController,
    calendar: Calendar = getInstance(),
    innerPadding: Dp,
    modalBottomSheetState: ModalBottomSheetState,
) {

    val scope = rememberCoroutineScope()

    CalendarContent(
        innerPadding = innerPadding,
        calendar = calendar,
    onDayEmptyTracks = {
        navController.setNavigationResult(SELECT_DAY_ADD, it)
    },
        onDayClick = {
            scope.launch {
                navController.currentBackStackEntry?.savedStateHandle?.set(SELECT_DATE, it)
                modalBottomSheetState.show()
            }
        }
    )
}





