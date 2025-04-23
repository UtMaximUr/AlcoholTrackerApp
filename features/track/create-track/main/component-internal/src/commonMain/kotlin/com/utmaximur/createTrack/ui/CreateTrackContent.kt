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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utmaximur.core.mvi_mapper.RequestUi
import com.utmaximur.design.RequestWidget
import com.utmaximur.design.extensions.bounceClick
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.design.ui.dots.DotsIndicator
import com.utmaximur.domain.Drink
import com.utmaximur.domain.TrackData
import features.track.create_track.main.Res
import features.track.create_track.main.add_degree
import features.track.create_track.main.add_event
import features.track.create_track.main.add_price
import features.track.create_track.main.add_quantity
import features.track.create_track.main.add_volume
import features.track.create_track.main.cd_calculator
import features.track.create_track.main.cd_event
import features.track.create_track.main.cd_price
import features.track.create_track.main.ic_calculate_white_24dp
import features.track.create_track.main.ic_event_24dp
import features.track.create_track.main.ic_local_bar_white_24dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CreateTrackContent(
    price: Float,
    currency: String,
    trackData: TrackData.Builder,
    requestDrinksUi: RequestUi<List<Drink>>,
    onCalculatorClick: () -> Unit,
    onCurrencyClick: () -> Unit,
    onDeleteClick: (Long) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        RequestWidget(
            state = requestDrinksUi,
            shimmerContentTemplate = { ItemDrinkShimmer() },
        ) { drinks ->
            val pagerState = rememberPagerState(pageCount = { drinks.size })
            LaunchedEffect(pagerState.currentPage, drinks) {
                drinks.getOrNull(pagerState.currentPage)
                    ?.let(trackData::setDrink)
            }
            ElevatedCardApp {
                Box(
                    modifier = Modifier.aspectRatio(4 / 3f),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    HorizontalPager(
                        state = pagerState,
                        pageContent = { index ->
                            ItemDrink(
                                drink = drinks[index],
                                onDeleteClick = onDeleteClick,
                            )
                        },
                    )
                    DotsIndicator(
                        modifier = Modifier.padding(bottom = 12.dp),
                        pageCount = pagerState.pageCount,
                        currentPageFraction = remember {
                            derivedStateOf {
                                pagerState.currentPage + pagerState.currentPageOffsetFraction
                            }
                        },
                    )
                }
            }
        }

        ElevatedCardApp(
            contentPaddingValues = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            InnerShadowTextField(
                title = stringResource(Res.string.add_quantity),
                keyboardType = KeyboardType.Number,
                onValueChange = trackData::setQuantity,
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_volume),
                keyboardType = KeyboardType.Decimal,
                onValueChange = trackData::setVolume,
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_degree),
                keyboardType = KeyboardType.Decimal,
                onValueChange = trackData::setDegree,
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_event),
                onValueChange = trackData::setEvent,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_event_24dp),
                        contentDescription = stringResource(Res.string.cd_event),
                        tint = MaterialTheme.colorScheme.tertiary,
                    )
                },
            )
            InnerShadowTextField(
                title = stringResource(Res.string.add_price),
                textValue = price,
                keyboardType = KeyboardType.Decimal,
                onValueChange = trackData::setPrice,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_local_bar_white_24dp),
                        contentDescription = stringResource(Res.string.cd_price),
                        tint = MaterialTheme.colorScheme.tertiary,
                    )
                },
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(
                            modifier = Modifier.bounceClick(),
                            shape = MaterialTheme.shapes.large,
                            onClick = onCurrencyClick,
                        ) {
                            Text(
                                text = currency,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Icon(
                            modifier = Modifier.clickable(onClick = onCalculatorClick),
                            painter = painterResource(Res.drawable.ic_calculate_white_24dp),
                            contentDescription = stringResource(Res.string.cd_calculator),
                            tint = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                },
            )
        }
    }
}
