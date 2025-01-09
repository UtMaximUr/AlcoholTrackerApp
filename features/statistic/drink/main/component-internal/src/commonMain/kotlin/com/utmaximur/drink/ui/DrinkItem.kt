package com.utmaximur.drink.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.utmaximur.drink.model.DrinkStatistic
import drinkStatistic.resources.Res
import drinkStatistic.resources.count_drink
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrinkItem(
    drink: DrinkStatistic
) {
    Column(
        modifier = Modifier.padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = Modifier.size(48.dp),
                model = drink.icon,
                contentDescription = drink.name
            )
            Text(
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(Res.string.count_drink, drink.countsDrink),
            )
        }
        Text(
            text = drink.name,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}