package com.utmaximur.feature_calendar.track_list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.utils.formatVolume
import com.utmaximur.utils.getResString
import com.utmaximur.utils.getSafeDoseOfAlcohol
import com.utmaximur.domain.entity.Track
import com.utmaximur.feature_calendar.R

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
                imageModel = track.image,
                contentDescription = stringResource(id = R.string.cd_image_drink)
            )
            AnimatedVisibility(
                visible = infoTrackVisibleState.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    TextField(
                        text = stringResource(
                            id = R.string.calendar_count_drink,
                            track.drink.getResString(LocalContext.current)!!,
                            track.quantity
                        ),
                        fontSize = 18.sp
                    )
                    Row {
                        ItemDrinkTitle(R.string.add_volume)
                        Spacer(modifier = Modifier.padding(6.dp))
                        TextField(
                            text = track.volume.formatVolume(
                                LocalContext.current,
                                track.quantity
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        ItemDrinkTitle(R.string.add_degree)
                        Spacer(modifier = Modifier.padding(6.dp))
                        TextField(text = track.degree)
                        Spacer(modifier = Modifier.padding(8.dp))
                        ItemDrinkTitle(R.string.add_currency)
                        Spacer(modifier = Modifier.padding(6.dp))
                        TextField(text = (track.price * track.quantity).toString())
                    }
                }
            }
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    idIcon = R.drawable.ic_info_24dp,
                    idText = R.string.cd_info_safe_dose,
                    trackInfoState = infoTrackVisibleState,
                    visibleState = eventVisibleState,
                    invisibleState = infoSafeDoseVisibleState
                )
                AnimatedVisibility(visible = track.event.isNotEmpty()) {
                    IconButton(
                        idIcon = R.drawable.ic_event_24dp,
                        idText = R.string.cd_event,
                        trackInfoState = infoTrackVisibleState,
                        visibleState = infoSafeDoseVisibleState,
                        invisibleState = eventVisibleState
                    )
                }
            }
            AnimatedVisibility(
                visible = infoSafeDoseVisibleState.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    TextField(
                        text = LocalContext.current.getSafeDoseOfAlcohol(
                            track.volume,
                            track.quantity,
                            track.degree
                        ),
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 18.sp
                    )
                    TextField(
                        text = stringResource(id = R.string.safe_dose_note),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = eventVisibleState.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TextField(
                    text = track.event,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun TextField(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = Color.White,
        fontSize = fontSize,
        modifier = modifier
    )
}

@Composable
fun IconButton(
    idIcon: Int,
    idText: Int,
    trackInfoState: MutableState<Boolean>,
    visibleState: MutableState<Boolean>,
    invisibleState: MutableState<Boolean>
) {
    Image(
        painter = if (!invisibleState.value) painterResource(id = idIcon) else painterResource(id = R.drawable.ic_local_bar_white_24dp),
        contentDescription = stringResource(id = idText),
        modifier = Modifier
            .clickable {
                invisibleState.value = !invisibleState.value
                if (visibleState.value) {
                    visibleState.value = !visibleState.value
                } else {
                    trackInfoState.value = !trackInfoState.value
                }
            }
            .padding(8.dp)
    )
}

@Composable
fun ItemDrinkTitle(
    text: Int,
    color: Color = Color.White
) {
    Text(
        text = stringResource(id = text),
        fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
        color = color
    )
}