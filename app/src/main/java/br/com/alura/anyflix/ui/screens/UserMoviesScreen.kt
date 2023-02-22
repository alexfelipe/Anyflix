package br.com.alura.anyflix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
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
import androidx.compose.ui.zIndex
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.theme.AnyFlixTheme
import coil.compose.AsyncImage

@Composable
fun MyListScreen(
    movies: List<Movie>,
    onSeeOtherMovies: () -> Unit,
    modifier: Modifier = Modifier,
    onMovieClick: (Movie) -> Unit,
    onRemoveMovieFromMyList: (Movie) -> Unit
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
                text = "My list",
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
                            .height(200.dp)
                            .clickable {
                                onMovieClick(movie)
                            },
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = movie.title)
                        Box {
                            Box(
                                Modifier
                                    .padding(16.dp)
                                    .size(50.dp)
                                    .background(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        shape = CircleShape
                                    )
                                    .align(Alignment.TopEnd)
                                    .clickable { onRemoveMovieFromMyList(movie) }
                            ) {
                                Icon(
                                    Icons.Default.Close, contentDescription = null, Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                            AsyncImage(
                                model = movie.image,
                                contentDescription = null,
                                Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .zIndex(-1f),
                                placeholder = ColorPainter(Color.Gray),
                                contentScale = ContentScale.Crop
                            )
                        }
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
            MyListScreen(
                sampleMovies,
                onSeeOtherMovies = {},
                onRemoveMovieFromMyList = {},
                onMovieClick = {}
            )
        }
    }
}

@Preview
@Composable
fun UserMoviesScreenWithoutMoviesPreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MyListScreen(
                movies = emptyList(),
                onSeeOtherMovies = {},
                onRemoveMovieFromMyList = {},
                onMovieClick = {}
            )
        }
    }
}

