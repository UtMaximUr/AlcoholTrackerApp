package com.utmaximur.feature_update

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UpdateDialog(viewModel: UpdateViewModel = hiltViewModel()) {

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        context.getActivity()?.let { activity ->
            viewModel.init(activity)
        }
    }

    val visibleState by viewModel.visibleState.observeAsState()

    AnimatedVisibility(visible = visibleState ?: false) {
        Card(
            modifier = Modifier.padding(bottom = 8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 11.dp
        ) {
            Column {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_update_black_24dp),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = stringResource(id = R.string.update_title),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                Row(modifier = Modifier.padding(16.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.onNowClick() })
                    {
                        Text(
                            text = stringResource(id = R.string.update_restart),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.onLaterClick() })
                    {
                        Text(
                            text = stringResource(id = R.string.update_later),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

private fun Context.getActivity(): Activity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}
