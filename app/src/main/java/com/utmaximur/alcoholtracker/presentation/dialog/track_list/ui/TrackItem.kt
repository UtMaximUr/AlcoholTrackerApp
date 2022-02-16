package com.utmaximur.alcoholtracker.presentation.dialog.track_list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.util.formatVolume
import com.utmaximur.alcoholtracker.util.getResString
import com.utmaximur.alcoholtracker.util.getSafeDoseOfAlcohol

@Composable
fun TrackItem(track: Track) {

    val infoTrackVisibleState = remember { mutableStateOf(true) }
    val eventVisibleState = remember { mutableStateOf(false) }
    val infoSafeDoseVisibleState = remember { mutableStateOf(false) }

    Box {
        Box(contentAlignment = Alignment.BottomStart) {
            GlideImage(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                imageModel = track.image
            )
            AnimatedVisibility(
                visible = infoTrackVisibleState.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = String.format(
                            LocalContext.current.resources.getString(R.string.calendar_count_drink),
                            track.drink.getResString(LocalContext.current), track.quantity
                        ),
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                        color = colorResource(id = R.color.text_color_white),
                        fontSize = 18.sp
                    )
                    Row {
                        ItemDrinkTitle(R.string.add_volume)
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(
                            text = track.volume.formatVolume(LocalContext.current, track.quantity),
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                            color = colorResource(id = R.color.text_color_white)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        ItemDrinkTitle(R.string.add_degree)
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(
                            text = track.degree,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                            color = colorResource(id = R.color.text_color_white)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        ItemDrinkTitle(R.string.add_currency)
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(
                            text = (track.price * track.quantity).toString(),
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                            color = colorResource(id = R.color.text_color_white)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_info_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            infoSafeDoseVisibleState.value = !infoSafeDoseVisibleState.value
                            if (eventVisibleState.value) {
                                eventVisibleState.value = !eventVisibleState.value
                            } else {
                                infoTrackVisibleState.value = !infoTrackVisibleState.value
                            }
                        }
                        .padding(8.dp)
                )
                AnimatedVisibility(visible = track.event.isNotEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_event_24dp),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                eventVisibleState.value = !eventVisibleState.value
                                if (infoSafeDoseVisibleState.value) {
                                    infoSafeDoseVisibleState.value = !infoSafeDoseVisibleState.value
                                } else {
                                    infoTrackVisibleState.value = !infoTrackVisibleState.value
                                }
                            }
                            .padding(8.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = infoSafeDoseVisibleState.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Text(
                        text = track.getSafeDoseOfAlcohol(LocalContext.current),
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                        color = colorResource(id = R.color.text_color_white),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.safe_dose_note),
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                        color = colorResource(id = R.color.text_color_white),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = eventVisibleState.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = track.event,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                    color = colorResource(id = R.color.text_color_white),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDrinkTitle(
    text: Int,
    color: Int = R.color.text_color_white
) {
    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = colorResource(id = color)
    )
}