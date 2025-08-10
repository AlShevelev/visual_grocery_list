package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.shevelev.visualgrocerylist.R

@Composable
internal fun ClearListConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        text = {
            Text(text = context.getString(R.string.clearListConfirmation))
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(text = context.getString(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = context.getString(R.string.no))
            }
        }
    )
}