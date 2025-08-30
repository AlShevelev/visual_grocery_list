package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun GeneralTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes iconResId: Int? = null,
    text: String,
) {
    val scope = rememberCoroutineScope()
    val dimensions = LocalDimensions.current

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            scope.launch {
                delay(300)
                onClick()
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (iconResId != null) {
                Icon(
                    painter = painterResource(iconResId),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(end = dimensions.paddingSingle)
                )
            }

            Text(text = text)
        }
    }
}