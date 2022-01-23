package com.utmaximur.alcoholtracker.presentation.settings


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.BuildConfig
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.settings.ui.theme.SettingsTheme
import com.utmaximur.alcoholtracker.util.*
import javax.inject.Inject


class SettingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<SettingsViewModel>

    private val viewModel: SettingsViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDagger()
        return ComposeView(requireContext()).apply {
            setContent {
                SettingsTheme {
                    Surface(color = colorResource(id = R.color.background_color)) {
                        SettingsView(viewModel)
                    }
                }
            }
        }
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }
}

@ExperimentalAnimationApi
@Composable
fun SettingsView(viewModel: SettingsViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val checkedThemeState by viewModel.checkedThemeState.observeAsState()
        val checkedUpdateState by viewModel.checkedUpdateState.observeAsState()

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(elevation = 11.dp) {
                Image(
                    modifier = Modifier.padding(20.dp),
                    painter = painterResource(id = R.drawable.ic_launcher),
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp, 0.dp, 12.dp, 0.dp),
                text = stringResource(id = R.string.settings_version) + " " + BuildConfig.VERSION_NAME,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
        }
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            backgroundColor = colorResource(id = R.color.purple_700),
            elevation = 4.dp
        ) {
            Column {
                Switch(R.string.default_theme, checkedThemeState!!) {
                    viewModel.onUseDefaultThemeChange(it)
                }
                ThemeList(checkedThemeState!!, viewModel)
                Switch(R.string.check_for_updates, checkedUpdateState!!) {
                    viewModel.onUpdateChange(it)
                }
                Button(R.string.privacy_policy, PRIVACY_POLICY)
                Button(R.string.terms_of_use, TERMS_OF_USE)
                Button(R.string.rate_app, MARKET_APP + LocalContext.current.packageName)
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ThemeList(visible: Boolean, viewModel: SettingsViewModel) {

    val themeLightCheckState by viewModel.themeLightCheckState.observeAsState()
    val themeDarkCheckState by viewModel.themeDarkCheckState.observeAsState()

    AnimatedVisibility(
        visible = !visible,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column(
            modifier = Modifier.background(colorResource(id = R.color.background_color))
        ) {
            ThemeItem(R.string.light_theme, themeLightCheckState!!) {
                viewModel.onLightThemeChange(it)
            }
            ThemeItem(R.string.dark_theme, themeDarkCheckState!!) {
                viewModel.onDarkThemeChange(it)
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ThemeItem(text: Int, themeCheckState: Boolean, onClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.clickable {
            onClick(!themeCheckState)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            text = stringResource(id = text),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
        AnimatedVisibility(visible = themeCheckState) {
            Image(
                modifier = Modifier.padding(end = 12.dp),
                painter = painterResource(id = R.drawable.ic_choice_24dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Switch(
    text: Int,
    checkedState: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            text = stringResource(id = text),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
        Switch(modifier = Modifier
            .padding(12.dp),
            checked = checkedState,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
                checkedTrackColor = MaterialTheme.colors.primary
            ),
            onCheckedChange = { onClick(it) })
    }
}

@Composable
fun Button(text: Int, url: String) {
    val context = LocalContext.current
    Button(
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
