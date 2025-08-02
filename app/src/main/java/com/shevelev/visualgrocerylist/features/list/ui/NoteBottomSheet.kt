package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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

    var isFocused by remember { mutableStateOf(false) }

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

            TextField(
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        text = context.getString(R.string.enter_note),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(
                                bottom = if (isFocused || text.isNotEmpty())
                                    dimensions.paddingSingle
                                else
                                    0.dp
                            )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                singleLine = false,
                maxLines = 5,
                textStyle = MaterialTheme.typography.bodyLarge,
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