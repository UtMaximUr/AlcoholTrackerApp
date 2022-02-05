package com.utmaximur.alcoholtracker.presentation.settings.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utmaximur.alcoholtracker.R

@Composable
fun Button(text: Int, url: String) {
    val context = LocalContext.current
    androidx.compose.material.Button(
        modifier = Modifier
            .padding(12.dp, 0.dp, 12.dp, 14.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(50),
        onClick = {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            )
        }
    ) {
        Text(
            text = stringResource(id = text),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color_white)
        )
    }
}