package br.com.alura.anyflix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.randomImage
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.theme.AnyFlixTheme
import coil.compose.AsyncImage

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    onAddToMyListClick: (Movie) -> Unit
) {
    val movies = remember {
        sampleMovies.shuffled()
    }
    val imageHeight = remember {
        200.dp
    }
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AsyncImage(
                model = movie.image, contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(RoundedCornerShape(8.dp))
                    .drawWithContent {
                        val colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f),
                        )
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors
                            ),
                        )
                    },
                placeholder = ColorPainter(Color.Gray),
                contentScale = ContentScale.Crop
            )

            Row(
                Modifier
                    .padding(16.dp)
                    .offset(y = imageHeight / 2)
            ) {
                AsyncImage(
                    model = movie.image, contentDescription = null,
                    Modifier
                        .width(150.dp)
                        .height(imageHeight)
                        .clip(RoundedCornerShape(8.dp)),
                    placeholder = ColorPainter(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row(
            modifier = Modifier
                .height(imageHeight / 2)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    Modifier
                        .widthIn(50.dp)
                        .clickable {
                            onAddToMyListClick(movie)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text(
                        text = "Add to my list",
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    Modifier.widthIn(50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Info, contentDescription = null)
                    Text(text = "Info")
                }
            }

        }
        Box(
            Modifier
                .padding(16.dp)

                .fillMaxWidth()
                .background(
                    Color.Red,
                    shape = RoundedCornerShape(50)
                )
                .clip(RoundedCornerShape(50))
                .clickable { }
                .padding(8.dp)
        ) {
            Row(
                Modifier.align(Center)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null
                )
                Text(
                    text = "Play",
                    textAlign = TextAlign.Center
                )
            }
        }
        Text(
            text = LoremIpsum(30)
                .values.first(),
            Modifier.padding(16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(3) {
                AsyncImage(
                    model = randomImage,
                    contentDescription = null,
                    Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    placeholder = ColorPainter(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            2.dp, Color.White,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                ) {
                    Icon(
                        Icons.Default.MoreHoriz,
                        contentDescription = "Mais atores",
                        Modifier.align(Center)
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = LoremIpsum(3).values.first().toString(),
                Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
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

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieDetailsScreen(
                sampleMovies.random(),
                onMovieClick = {},
                onAddToMyListClick = {}
            )
        }
    }
}