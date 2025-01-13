package com.andresp.ontoptest.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andresp.ontoptest.presentation.capitalizePhrase

// Functionality 2: Capitalize Input
@Composable
fun CapitalizeScreen(onBackPressed: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Capitalize Text",
                onBackPressed = onBackPressed
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(text = "Enter the text you want to capitalize", modifier = Modifier.padding(16.dp))
            TextField(
                value = input,
                onValueChange = {
                    input = it
                    result = capitalizePhrase(it)
                },
                label = { Text("Your text") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Text(text = result, modifier = Modifier.padding(16.dp))
        }
    }
}