package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.GeneralTextButton
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectImageDialog(
    onDismiss: () -> Unit,
    onCamera: () -> Unit,
    onGallery: () -> Unit,
    onSearch: () -> Unit,
) {
    val context = LocalContext.current
    val dimensions = LocalDimensions.current

    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensions.dialogCorners),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = dimensions.dialogContent)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = context.getString(R.string.edit_image_using),
                    style = MaterialTheme.typography.titleMedium,
                )

                GeneralTextButton(
                    modifier = Modifier.padding(top = dimensions.paddingDoubleAndHalf),
                    iconResId = R.drawable.ic_photo_camera_24,
                    onClick = {
                        //onConfirmation(name, item)
                    },
                    text = context.getString(R.string.camera),
                )
                GeneralTextButton(
                    modifier = Modifier.padding(top = dimensions.paddingSingle),
                    iconResId = R.drawable.ic_image_24,
                    onClick = {
                        //onConfirmation(name, item)
                    },
                    text = context.getString(R.string.gallery),
                )
                GeneralTextButton(
                    modifier = Modifier.padding(top = dimensions.paddingSingle),
                    iconResId = R.drawable.ic_internet_24,
                    onClick = {
                        //onConfirmation(name, item)
                    },
                    text = context.getString(R.string.searching_internet),
                )
            }
        }
    }
}
