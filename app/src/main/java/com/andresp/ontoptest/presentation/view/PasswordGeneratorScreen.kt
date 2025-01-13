package com.andresp.ontoptest.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andresp.ontoptest.presentation.generatePassword

// Functionality 3: Password Generator
@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun PasswordGeneratorScreen(onBackPressed: () -> Unit) {
    var sliderValue by remember { mutableStateOf(8f) }
    var includeUppercase by remember { mutableStateOf(true) }
    var includeNumbers by remember { mutableStateOf(true) }
    var includeSymbols by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Password Generator",
                onBackPressed = onBackPressed
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            Text(
                "Select the amount of characters you want your password to be (5-20)",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodySmall,
            )
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 5f..20f,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally).padding(horizontal = 12.dp)
            )

            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = "Selected characters: ${sliderValue.toInt()}",
                style = MaterialTheme.typography.bodySmall
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = includeUppercase, onCheckedChange = { includeUppercase = it })
                Text("Include Uppercase")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = includeNumbers, onCheckedChange = { includeNumbers = it })
                Text("Include Numbers")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = includeSymbols, onCheckedChange = { includeSymbols = it })
                Text("Include Symbols")
            }
            if (password.isNotEmpty()) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Generated password: ")
                    SelectionContainer {
                        Text(
                            text = password,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
            Button(modifier = Modifier.fillMaxWidth().padding(16.dp), onClick = {
                password =
                    generatePassword(sliderValue.toInt(), includeUppercase, includeNumbers, includeSymbols)
            }) {
                Text("Generate Password")
            }
        }
    }
}