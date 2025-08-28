package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions

@Composable
internal fun ConfirmationDialog(
    text: String,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
            )
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
                Text(
                    text = context.getString(R.string.yes),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(
                    text = context.getString(R.string.no),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    )
}

@Composable
internal fun <T>NameConfirmationDialog(
    item: T,
    startName: String,
    onDismiss: () -> Unit,
    onConfirmation: (String, T) -> Unit,
) {
    val context = LocalContext.current
    val dimensions = LocalDimensions.current

    var name by remember { mutableStateOf(startName) }

    AlertDialog(
        title = {
            Text(
                text = context.getString(R.string.the_name_is),
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            BasicTextField(
                value = name,
                onValueChange = {
                    if (it.length <= 30) {
                        name = it
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .border(
                                width = dimensions.paddingQuarter,
                                color = MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(percent = 30)
                            )
                            .padding(dimensions.paddingSingleAndHalf)
                    ) {
                        innerTextField()
                    }
                }
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                enabled = name.isNotBlank() && name.isNotEmpty(),
                onClick = {
                    onConfirmation(name, item)
                }
            ) {
                Text(
                    text = context.getString(R.string.save),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
    )
}
