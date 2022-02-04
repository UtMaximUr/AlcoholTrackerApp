package com.utmaximur.alcoholtracker.presentation.create_track.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.util.getResString

@Composable
fun ItemDrink(
    drink: Drink,
    context: Context = LocalContext.current
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(drink.photo),
            contentDescription = null
        )
        Card(
            modifier = Modifier
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = 2.dp
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = drink.drink.getResString(context),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
        }
    }
}