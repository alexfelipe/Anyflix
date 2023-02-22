package br.com.alura.anyflix.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.components.AnyflixBottomAppBar
import br.com.alura.anyflix.ui.components.AnyflixMainBanner
import br.com.alura.anyflix.ui.components.AnyflixTopAppBar
import br.com.alura.anyflix.ui.theme.AnyFlixTheme
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    myList: List<Movie>,
    onMovieClick: (Movie) -> Unit = {},
    onProfileMenuClick: () -> Unit = {},
    onMyListClick: () -> Unit = {}
) {
    Box {
        AnyflixTopAppBar(
            title = "Anyflix",
            modifier = Modifier
                .zIndex(1f)
                .drawWithContent {
                    val colors = listOf(
                        Color.Black,
                        Color.Transparent
                    )
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.Darken
                    )
                    drawContent()
                },
            onProfileMenuClick = onProfileMenuClick,
        )
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = 80.dp
            )
        ) {
            item {
                val movie = remember {
                    sampleMovies.random()
                }
                AnyflixMainBanner(
                    movie = movie,
                    onMovieClick = onMovieClick,
                    onMyListClick = onMyListClick
                )
            }
            if (myList.isNotEmpty()) {
                item {
                    val title = "My list"
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
                            items(myList) { movie ->
                                Box {
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
            items(3) {
                val title = LoremIpsum(2).values.first().toString()
                val movies = sampleMovies.shuffled()
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
                            Box {
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
        AnyflixBottomAppBar(
            Modifier
                .align(
                    Alignment.BottomCenter
                )
                .zIndex(1f)
                .drawWithContent {
                    val colors = listOf(
                        Color.Transparent,
                        Color.Black
                    )
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.Darken
                    )
                    drawContent()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(emptyList())
        }
    }
}
