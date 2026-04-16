package com.project.designsystem.compose

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LocarmTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }

    OutlinedTextField(
        modifier = modifier,
        label = { Text(text = label) },
        placeholder = placeholder?.let { { Text(it) } },
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            onValueChange(newValue.text)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done,
        ),
    )
}

@Composable
fun LocarmNumberTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
) {
    LocarmTextField(
        modifier = modifier,
        value = value,
        label = label,
        placeholder = placeholder,
        onValueChange = onValueChange,
        keyboardType = KeyboardType.Number
    )
}

@Composable
fun LocarmDateTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number,
) {
    OutlinedTextField(
        modifier = modifier,
        label = { Text(text = label) },
        placeholder = placeholder?.let { { Text(it) } },
        value = TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        ),
        onValueChange = { newValue ->
            onValueChange(newValue.text)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done,
        ),
    )
}

@Preview
@Composable
private fun LocarmTextFieldPreview() {
    LocarmTextField(
        value = "",
        label = "테스트",
        placeholder = "PlaceHolder Test",
        onValueChange = {},
    )
}
