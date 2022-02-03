package com.utmaximur.alcoholtracker.presentation.create_track.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utmaximur.alcoholtracker.R

@Composable
fun ToolBar(
    title: Int?,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = title ?: R.string.add_drink_title))
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackClick()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = colorResource(id = R.color.text_color),
        elevation = 2.dp,
        actions = {
            IconButton(onClick = { onSaveClick() }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null
                )
            }
        }
    )
}