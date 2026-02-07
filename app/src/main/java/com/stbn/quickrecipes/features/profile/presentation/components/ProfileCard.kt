package com.stbn.quickrecipes.features.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stbn.quickrecipes.features.auth.domain.model.User

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    user: User ?= null
) {
    Card(
        modifier = modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(5),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 100.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                modifier = Modifier.size(75.dp),
                imageVector = Icons.Default.Fastfood,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
            Text(text = user?.name ?: "")
            Text(text = "Amante de la cocina")
            UserItemInfo(
                icon = Icons.Outlined.PersonOutline,
                title = "Nombre",
                info = user?.name ?: ""
            )
            UserItemInfo(
                icon = Icons.Outlined.MailOutline,
                title = "Correo electrónico",
                info = user?.email ?: ""
            )
        }
    }
}

@Composable
private fun UserItemInfo(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    info: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(text = info)
        }
    }
}