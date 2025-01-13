package com.andresp.ontoptest.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andresp.ontoptest.presentation.viewmodel.SharedCharacterViewModel

@Composable
fun DetailsScreen(sharedViewModel: SharedCharacterViewModel, onBackPressed: () -> Unit) {

    val selectedCharacter = sharedViewModel.selectedCharacter.value
    if (selectedCharacter != null) {
        Scaffold(
            topBar = {
                AppToolbar(
                    title = selectedCharacter.name,
                    onBackPressed = onBackPressed
                )
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxWidth()) {
                AsyncImage(
                    model = selectedCharacter.image,
                    contentDescription = selectedCharacter.name,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.5f) // Occupy half of screen
                        .clip(RoundedCornerShape(8.dp))
                        .aspectRatio(1f)
                        .align(Alignment.CenterHorizontally)
                )
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Name: ")
                    }
                    append(selectedCharacter.name)
                }, modifier = Modifier.padding(top = 16.dp, start = 16.dp))
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Is Alive: ")
                    }
                    append(if (selectedCharacter.isAlive) "Yes" else "No")
                }, modifier = Modifier.padding(top = 16.dp, start = 16.dp))
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Species: ")
                    }
                    append(selectedCharacter.species)
                }, modifier = Modifier.padding(top = 16.dp, start = 16.dp))
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Gender: ")
                    }
                    append(selectedCharacter.gender)
                }, modifier = Modifier.padding(top = 16.dp, start = 16.dp))
            }
        }
    }
}