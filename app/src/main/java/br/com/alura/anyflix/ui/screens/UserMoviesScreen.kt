package br.com.alura.anyflix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.theme.AnyFlixTheme
import coil.compose.AsyncImage

@Composable
fun UserMoviesScreen(
    movies: List<Movie>,
    onSeeOtherMovies: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty()) {
        Box(
            Modifier.fillMaxSize()
        ) {
            Column(
                Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sem filmes favoritados",
                    style = MaterialTheme.typography.titleLarge
                )
                TextButton(onClick = onSeeOtherMovies) {
                    Text(text = "Ver outros filmes")
                }
            }
        }
    } else {
        Column {
            Text(
                text = "My favorite movies",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies) { movie ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = movie.title)
                        AsyncImage(
                            model = movie.image,
                            contentDescription = null,
                            Modifier
                                .clip(RoundedCornerShape(8.dp)),
                            placeholder = ColorPainter(Color.Gray),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun UserMoviesScreenPreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserMoviesScreen(
                sampleMovies,
                onSeeOtherMovies = {}
            )
        }
    }
}

@Preview
@Composable
fun UserMoviesScreenWithoutMoviesPreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserMoviesScreen(
                movies = emptyList(),
                onSeeOtherMovies = {}
            )
        }
    }
}

