package com.stbn.quickrecipes.features.recipes.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CuisineItem(
    modifier: Modifier = Modifier,
    cuisine: String,
    selected: Boolean = false,
    onClick: (String) -> Unit
) {
    val shape = RoundedCornerShape(50)
    val backgroundColor = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
    val borderColor = if (selected) Color.Transparent else MaterialTheme.colorScheme.primary
    val textColor = if (selected) Color.White else MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier
            .clip(shape)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .background(backgroundColor)
            .clickable { onClick(cuisine) }
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = cuisine,
            color = textColor
        )
    }
}