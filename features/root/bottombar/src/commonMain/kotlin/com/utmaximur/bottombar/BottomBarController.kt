package com.utmaximur.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy

/**
 * Абстрактный контроллер для управления видимостью BottomBar.
 * Позволяет:
 * - Отслеживать состояние через [state]
 * - Показывать/скрывать BottomBar
 * - Автоматически восстанавливать видимость при уничтожении Lifecycle.
 */
abstract class BottomBarController {
    /**
     * Текущее состояние BottomBar. Реактивно обновляется в Compose.
     */
    abstract val state: State<BottomBarState>

    /** Показать BottomBar */
    abstract fun show()

    /** Скрыть BottomBar */
    abstract fun hide()

    /**
     * Связать скрытие BottomBar с жизненным циклом компонента.
     * При уничтожении [lifecycle] BottomBar будет автоматически показан.
     * @param lifecycle Жизненный цикл (например, Activity/Fragment)
     */
    abstract fun hideToLifecycle(lifecycle: Lifecycle)
}

/**
 * Состояние BottomBar.
 * @param visible Флаг видимости (по умолчанию `true`).
 */
data class BottomBarState(
    val visible: Boolean = true,
)

/**
 * Реализация [BottomBarController] для Jetpack Compose.
 * @param initialState Начальное состояние BottomBar.
 */
internal class DefaultBottomBarController(initialState: BottomBarState) : BottomBarController() {
    private var _state: MutableState<BottomBarState> = mutableStateOf(initialState)
    override val state: State<BottomBarState> = _state

    override fun show() {
        _state.value = state.value.copy(visible = true)
    }

    override fun hide() {
        _state.value = state.value.copy(visible = false)
    }

    override fun hideToLifecycle(lifecycle: Lifecycle) {
        if (!_state.value.visible) return
        hide()
        lifecycle.doOnDestroy { show() }
    }
}

/**
 * Локальный провайдер для доступа к [BottomBarController] внутри Compose.
 * По умолчанию создает экземпляр [DefaultBottomBarController].
 */
val LocalBottomBarController: ProvidableCompositionLocal<BottomBarController> =
    staticCompositionLocalOf {
        DefaultBottomBarController(BottomBarState())
    }

/**
 * Компонент для предоставления [BottomBarController] потомкам.
 * Размещается в корне UI-иерархии.
 */
@Composable
fun ProvideBottomBarController(initialState: BottomBarState = BottomBarState()) =
    LocalBottomBarController provides DefaultBottomBarController(initialState)