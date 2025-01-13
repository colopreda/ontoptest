package com.andresp.ontoptest.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.andresp.ontoptest.presentation.model.CharacterModel
import com.andresp.ontoptest.presentation.viewmodel.ApiViewModel
import com.andresp.ontoptest.presentation.viewmodel.SharedCharacterViewModel
import com.andresp.ontoptest.ui.theme.Purple80

// Functionality 1: Consume API REST with pagination
@Composable
fun PaginatedListScreen(navController: NavController, sharedCharacterViewModel: SharedCharacterViewModel, onBackPressed: () -> Unit = { navController.popBackStack() } ) {
    val viewModel: ApiViewModel = hiltViewModel()
    var query by remember { mutableStateOf("") }
    val characters = viewModel.characters.collectAsLazyPagingItems()

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Paginated List",
                onBackPressed = onBackPressed
            )
        }
    ) { padding ->
        when {
            characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 -> {
                LoadingCharacters()
            }
            characters.loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 -> {
                NoItemsFound()
            }
            characters.loadState.hasError -> {
                LoadError()
            }
        }

        Column(modifier = Modifier.padding(padding).padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                },
                label = { Text("Search") },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = {
                            query = ""
                            focusManager.clearFocus()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                if (query.isNotEmpty()) {
                    // Use snapshot to search
                    val filteredCharacters = characters.itemSnapshotList.items.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                    if(filteredCharacters.isEmpty()) {
                        item {
                            if (characters.loadState.refresh is LoadState.NotLoading && query.isNotEmpty()) {
                                NoItemsFound()
                            }
                        }
                    } else {
                        items(filteredCharacters.size) { pos ->
                            val character = filteredCharacters[pos]
                            ItemList(character, navController, sharedCharacterViewModel)
                        }
                    }
                } else {
                    items(characters.itemCount) { pos ->
                        val character = characters[pos]
                        if (character != null) {
                            ItemList(character, navController, sharedCharacterViewModel)
                        }
                    }
                    if(characters.loadState.append is LoadState.Loading) {
                        item {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(32.dp),
                                    color = Color.Blue
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun ItemList(character: CharacterModel, navController: NavController, sharedCharacterViewModel: SharedCharacterViewModel) {
    Card(
        onClick = {
            sharedCharacterViewModel.setCharacter(character)
            navController.navigate("details")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Purple80)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )

            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
        }
    }
}

@Composable
private fun LoadingCharacters() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Loading characters...",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun NoItemsFound() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "No Items",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = "No characters found.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun LoadError() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Error",
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = "An error occurred.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}