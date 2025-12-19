package com.stbn.quickrecipes.features.auth.presentation.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginTextField (
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    title: String? = null,
    placeholder: String? = null,
    isSecret: Boolean = false
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (!title.isNullOrEmpty()) {
            Text(
                text = title
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (!placeholder.isNullOrEmpty()) {
                    Text(
                        text = placeholder
                    )
                }
            },
            singleLine = true,
            visualTransformation = if (isSecret) PasswordVisualTransformation() else VisualTransformation.None,
            shape = RoundedCornerShape(20),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.Gray, unfocusedPlaceholderColor = Color.Gray)
        )
    }
}