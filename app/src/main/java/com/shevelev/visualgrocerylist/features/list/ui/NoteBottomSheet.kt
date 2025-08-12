package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.NotePopup
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel.UserActionsHandler
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteBottomSheet(
    notePopup: NotePopup,
    userActionsHandler: UserActionsHandler,
) {
    val context = LocalContext.current
    val dimensions = LocalDimensions.current

    val sheetState = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    ModalBottomSheet(
        onDismissRequest = userActionsHandler::onNotePopupDismissed,
        sheetState = sheetState
    ) {
        var text by remember { mutableStateOf(notePopup.note.orEmpty()) }

        Column(
            modifier = Modifier.padding(horizontal = dimensions.paddingDouble),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensions.paddingDouble)
        ) {
            Text(
                text = context.getString(R.string.note_sheet_caption, notePopup.title),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge
            )

            BasicTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = false,
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth(),
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

            Button(onClick = {
                keyboardController?.hide()
                scope.launch {
                    sheetState.hide()
                    userActionsHandler.onUpdateNote(notePopup.dbId, text)
                }
            }) {
                Text(text = context.getString(R.string.save))
            }
        }
    }
}