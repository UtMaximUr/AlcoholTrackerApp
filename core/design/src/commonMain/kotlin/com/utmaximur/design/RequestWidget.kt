package com.utmaximur.design

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.mappers.implementation.LoadStateType


@Composable
fun <T : Any> RequestWidget(
    state: RequestUi<T>,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit = {},
    shimmerContentTemplate: @Composable () -> Unit = { },
    emptyContentTemplate: @Composable () -> Unit = { },
    content: @Composable (data: T) -> Unit
) {
    val (data, _, error) = state

    when {

        data != null -> content(data)

        state.isLoading -> shimmerContentTemplate()

        state.load is LoadStateType.Empty -> emptyContentTemplate()

        error != null -> ErrorPlaceholder(
            errorMessage = "$error",
            onRetryClick = onRetryClick,
            modifier = modifier
        )
    }
}