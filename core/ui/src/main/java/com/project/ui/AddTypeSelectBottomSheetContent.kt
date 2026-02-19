package com.project.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun AddTypeSelectBottomSheetContent(
    modifier: Modifier = Modifier,
    onBarcodeScannerClick: () -> Unit,
    onDirectInputClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextButton(
            modifier = modifier
                .fillMaxWidth(),
            onClick = { onBarcodeScannerClick() }
        ) {
            Text(stringResource(R.string.barcodeScanner))
        }

        TextButton(
            modifier = modifier
                .fillMaxWidth(),
            onClick = { onDirectInputClick() }
        ) {
            Text(stringResource(R.string.directInput))
        }
    }
}
