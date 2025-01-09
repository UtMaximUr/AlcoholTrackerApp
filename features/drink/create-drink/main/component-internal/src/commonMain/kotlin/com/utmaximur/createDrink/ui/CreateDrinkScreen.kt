package com.utmaximur.createDrink.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.utmaximur.createDrink.CreateDrinkComponent
import com.utmaximur.createDrink.DrinkData
import com.utmaximur.design.extensions.bounceClick
import com.utmaximur.design.text.InnerShadowTextField
import com.utmaximur.design.topbar.TopBar
import com.utmaximur.design.ui.ElevatedCardApp
import createDrink.resources.Res
import createDrink.resources.cd_drink_image
import createDrink.resources.placeholder_image
import createDrink.resources.save_drink
import createDrink.resources.title_create_drink
import createDrink.resources.title_name_drink
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun CreateDrinkScreen(
    component: CreateDrinkComponent
) {
    val state by component.model.collectAsState()
    val drinkBuilder = remember { DrinkData.Builder() }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = component::navigateBack,
                title = stringResource(Res.string.title_create_drink)
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ElevatedCardApp(
                    modifier = Modifier
                        .bounceClick()
                        .clickable(onClick = component::openImageActionDialog)
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.aspectRatio(4 / 3f),
                        model = state.url,
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(Res.string.cd_drink_image),
                        onSuccess = { drinkBuilder.setPhoto(state.url) },
                        error = {
                            Image(
                                painter = painterResource(Res.drawable.placeholder_image),
                                contentDescription = stringResource(Res.string.cd_drink_image)
                            )
                        }
                    )
                }
                ElevatedCardApp(
                    contentPaddingValues = PaddingValues(12.dp)
                ) {
                    InnerShadowTextField(
                        title = stringResource(Res.string.title_name_drink),
                        onValueChange = drinkBuilder::setName
                    )
                }
                DrinksIconContent(
                    icons = state.icons,
                    onSelectIcon = drinkBuilder::setIcon
                )
            }
        },
        bottomBar = {
            TextButton(
                modifier = Modifier
                    .padding(16.dp)
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .bounceClick(),
                onClick = { component.onSaveClick(drinkBuilder.build()) },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = stringResource(Res.string.save_drink),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    )
}