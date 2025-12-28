package com.stbn.quickrecipes.features.recipes.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stbn.quickrecipes.features.recipes.components.CatalogItem
import com.stbn.quickrecipes.features.recipes.components.CatalogTopBar

@Composable
fun CatalogScreenRoot(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CatalogTopBar()
        CatalogScreen(
            modifier = modifier
        )
    }
}

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 15.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Explora Nuestras Recetas",
            fontSize = 20.sp
        )
        Text(
            text = "Descubre platillos deliciosos para cada ocasión"
        )
        CatalogItem()
    }
}