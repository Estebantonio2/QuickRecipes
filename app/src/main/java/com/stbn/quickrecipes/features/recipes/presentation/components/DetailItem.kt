package com.stbn.quickrecipes.features.recipes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.stbn.quickrecipes.features.recipes.domain.model.RecipeDetail

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    image: String ?= null,
    titleIcon: ImageVector ?= null,
    title: String ?= null,
    description: String ?= null,
    subtitles: List<Pair<ImageVector, String>> ?= null,
    isListed: Boolean = false,
    recipe: RecipeDetail ?= null
) {
    Card(
        modifier = modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(5),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 100.dp)
    ) {
        if (image != null) {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (titleIcon != null) {
                        Icon(
                            imageVector = titleIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (title != null) {
                        Text(
                            text = title,
                            fontSize = 20.sp
                        )
                    }
                }
                if (subtitles != null) {
                    Subtitles(
                        subtitles = subtitles
                    )
                }
                if (description != null) {
                    Text(
                        text = description,
                        textAlign = TextAlign.Justify,
                        letterSpacing = 0.sp
                    )
                }
                if (recipe != null) {
                    IngredientsList(
                        isListed = isListed,
                        recipe = recipe
                    )
                }
            }
        }
    }
}

@Composable
private fun IngredientsList(
    modifier: Modifier = Modifier,
    isListed: Boolean,
    recipe: RecipeDetail
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        when {
            !isListed -> {
                recipe.ingredients.forEach { ingredient ->
                    BulletItem(
                        text = ingredient.description
                    )
                }
            }
            else -> {
                recipe.steps.forEachIndexed { index, step ->
                    ListedItem(
                        index = (index + 1).toString(),
                        text = step.description
                    )
                }
            }
        }
    }
}

@Composable
private fun Subtitles(
    modifier: Modifier = Modifier,
    subtitles: List<Pair<ImageVector, String>>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        subtitles.forEach { subtitle ->
            Sub(
                icon = subtitle.first,
                title = subtitle.second
            )
        }
    }
}

@Composable
private fun Sub(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title
        )
    }
}

@Composable
private fun BulletItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "\u2022",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.weight(1f),
            text = text
        )
    }
}

@Composable
private fun ListedItem(
    modifier: Modifier = Modifier,
    index: String,
    text: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = index,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = text
        )
    }
}