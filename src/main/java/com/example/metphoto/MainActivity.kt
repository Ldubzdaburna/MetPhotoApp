
package com.example.metphoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.metphoto.viewModels.ArtworkDetails
import com.example.metphoto.viewModels.MetPhotoViewModel


class MainActivity : ComponentActivity() {
    private val viewModel: MetPhotoViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val artworks by viewModel.artworks.collectAsState()

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Metropolitan Museum of Art") }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { viewModel.loadMore() }) {
                        Text("+5")
                    }
                }
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(artworks) { artwork ->
                        ArtworkItem(artwork)
                    }
                }
            }
        }
    }
}

@Composable
fun ArtworkItem(artwork: ArtworkDetails) {
    var showDetails by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Image(
            painter = rememberAsyncImagePainter(model = artwork.imageUrl),
            contentDescription = "Met Image ${artwork.objectID}",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { showDetails = !showDetails }) {
                Text(if (showDetails) "Hide Details" else "View Details")
            }
        }

        if (showDetails) {
            Text("Title: ${artwork.title}", style = MaterialTheme.typography.bodyLarge)
            Text("Year: ${artwork.year}", style = MaterialTheme.typography.bodyMedium)
            Text("Artist/Origin: ${artwork.artist}", style = MaterialTheme.typography.bodySmall)
        }
    }
}