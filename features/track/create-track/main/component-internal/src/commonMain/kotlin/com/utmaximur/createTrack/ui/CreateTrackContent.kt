package com.utmaximur.createTrack.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.extensions.bounceClick
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.ui.DotsIndicator
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.TrackData
import createTrack.resources.Res
import createTrack.resources.add_degree
import createTrack.resources.add_event
import createTrack.resources.add_price
import createTrack.resources.add_quantity
import createTrack.resources.add_volume
import createTrack.resources.cd_calculator
import createTrack.resources.cd_event
import createTrack.resources.cd_price
import createTrack.resources.ic_calculate_white_24dp
import createTrack.resources.ic_event_24dp
import createTrack.resources.ic_local_bar_white_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun CreateTrackContent(
    price: Float,
    currency: String,
    trackData: TrackData.Builder,
    requestDrinksUi: RequestUi<List<Drink>>,
    onCalculatorClick: () -> Unit,
    onCurrencyClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RequestWidget(
            state = requestDrinksUi,
            shimmerContentTemplate = { ItemDrinkShimmer() }
        ) { drinks ->
            val pagerState = rememberPagerState(pageCount = { drinks.size })
            LaunchedEffect(pagerState.currentPage, drinks) {
                drinks.getOrNull(pagerState.currentPage)
                    ?.let(trackData::setDrink)
            }
            ElevatedCardApp {
                Box(
                    modifier = Modifier.aspectRatio(4 / 3f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    HorizontalPager(
                        state = pagerState,
                        pageContent = { index ->
                            ItemDrink(drinks[index])
                        }
                    )
                    DotsIndicator(
                        modifier = Modifier.padding(bottom = 12.dp),
                        totalDots = drinks.size,
                        selectedIndex = pagerState.currentPage
                    )
                }
            }
        }

        ElevatedCardApp(
            contentPaddingValues = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            InnerShadowTextField(
                title = stringResource(Res.string.add_quantity),
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setQuantity
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_volume),
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setVolume
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_degree),
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setDegree
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_event),
                onValueChange = trackData::setEvent,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_event_24dp),
                        contentDescription = stringResource(Res.string.cd_event),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_price),
                textValue = price,
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setPrice,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_local_bar_white_24dp),
                        contentDescription = stringResource(Res.string.cd_price),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            modifier = Modifier.bounceClick(),
                            shape = MaterialTheme.shapes.large,
                            onClick = onCurrencyClick
                        ) {
                            Text(
                                text = currency,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Icon(
                            modifier = Modifier.clickable(onClick = onCalculatorClick),
                            painter = painterResource(Res.drawable.ic_calculate_white_24dp),
                            contentDescription = stringResource(Res.string.cd_calculator),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            )
        }
    }
}