package com.utmaximur.feature_create_track.create_track.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.utmaximur.feature_create_track.R
import com.utmaximur.utils.getResString
import com.utmaximur.domain.entity.Drink

@Composable
fun ItemDrink(
    drink: Drink?,
    context: Context = LocalContext.current
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GlideImage(
            imageModel = drink?.photo ?: R.raw.create_drink,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            contentDescription = stringResource(id = R.string.cd_image_drink)
        )
        Card(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = 2.dp
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = drink?.drink.getResString(context)
                    ?: stringResource(id = R.string.add_new_drink),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = MaterialTheme.colors.onPrimary,
            )
        }
    }
}