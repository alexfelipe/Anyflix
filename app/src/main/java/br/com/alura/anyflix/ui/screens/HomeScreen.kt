package br.com.alura.anyflix.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovieSections
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.components.AnyflixMainBanner
import br.com.alura.anyflix.ui.theme.AnyFlixTheme
import br.com.alura.anyflix.ui.uistates.HomeUiState
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onMovieClick: (Movie) -> Unit,
    onRetryLoadSections: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLoading = uiState.isLoading
    val error = uiState.error
    Log.i("HomeScreen", "HomeScreen: uiState $uiState")
    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        }
    } else {
        Box {
            error?.let {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            Color.Red.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(15)
                        )
                        .fillMaxWidth()
                        .zIndex(1f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(15)),
                ) {
                    Column {
                        Text(
                            text = "Falha ao carregar as seções",
                            Modifier.fillMaxWidth(),
                            style = TextStyle.Default.copy(
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            ),
                            textAlign = TextAlign.Center
                        )
                        TextButton(onClick = { onRetryLoadSections() }) {
                            Text(
                                text = "Tentar buscar novamente",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            uiState.sections?.let { sections ->
                when {
                    sections.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Nenhuma seção encontrada",
                                style = TextStyle.Default.copy(
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                                )
                            )
                            TextButton(onClick = { onRetryLoadSections() }) {
                                Text(text = "Tentar buscar novamente")
                            }
                        }
                    }
                    else -> {
                        Box(modifier) {
                            LazyColumn {
                                item {
                                    uiState.mainBannerMovie?.let { movie ->
                                        AnyflixMainBanner(
                                            movie = movie,
                                            Modifier.height(300.dp),
                                            onMovieClick = onMovieClick,
                                        )
                                    }
                                }
                                sections.forEach {
                                    val title = it.key
                                    val movies = it.value
                                    item {
                                        Column {
                                            Text(
                                                text = title,
                                                Modifier.padding(
                                                    horizontal = 16.dp,
                                                    vertical = 8.dp
                                                ),
                                                style = TextStyle.Default.copy(
                                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                                                )
                                            )
                                            LazyRow(
                                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                                contentPadding = PaddingValues(
                                                    horizontal = 16.dp,
                                                    vertical = 8.dp
                                                )
                                            ) {
                                                items(movies) { movie ->
                                                    AsyncImage(
                                                        model = movie.image,
                                                        contentDescription = null,
                                                        Modifier
                                                            .width(150.dp)
                                                            .height(200.dp)
                                                            .clip(RoundedCornerShape(8.dp))
                                                            .clickable {
                                                                onMovieClick(movie)
                                                            },
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
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(
                uiState = HomeUiState(
                    sections = sampleMovieSections,
                    mainBannerMovie = sampleMovies.random()
                ),
                onRetryLoadSections = {},
                onMovieClick = {}
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenWithoutSections() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(
                uiState = HomeUiState(),
                onRetryLoadSections = {},
                onMovieClick = {}
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenWithFailurePreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(
                uiState = HomeUiState(error = "falha ao buscar filmes"),
                onRetryLoadSections = {},
                onMovieClick = {}
            )
        }
    }
}