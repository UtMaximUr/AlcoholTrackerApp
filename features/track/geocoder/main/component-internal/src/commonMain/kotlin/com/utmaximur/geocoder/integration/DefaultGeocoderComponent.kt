package com.utmaximur.geocoder.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.domain.models.Place
import com.utmaximur.geocoder.GeocoderComponent
import com.utmaximur.geocoder.store.GeocoderStore
import com.utmaximur.geocoder.ui.GeocoderUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultGeocoderComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val placeOutputHandler: (place: Place) -> Unit,
    @InjectedParam trackId: Long?,
) : GeocoderComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: GeocoderStore = instanceKeeper.getStore {
        get { parametersOf(trackId) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<GeocoderStore.State> = store.stateFlow

    override fun handleQuery(query: String) =
        store.accept(GeocoderStore.Intent.Search(query))

    override fun handleSelectedPlace(place: Place) {
        store.accept(GeocoderStore.Intent.SelectedPlace(place))
        placeOutputHandler(place)
    }

    @Composable
    override fun Render(modifier: Modifier) = GeocoderUi(this)
}
