package br.com.alura.anyflix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.alura.anyflix.ui.theme.AnyFlixTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Box {
        CenterAlignedTopAppBar(
            title = {
                Box(
                    modifier =
                    Modifier
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = "Anyflix",
                        Modifier
                            .padding(
                                vertical = 4.dp,
                                horizontal = 16.dp
                            )
                            .align(Alignment.Center),
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent),
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.PersonOutline, contentDescription = null)
                }
            }
        )
        LazyColumn(Modifier.zIndex(-1f)) {
            item {
                Box(
                    Modifier
                        .height(500.dp)
                ) {
                    AsyncImage(
                        model = "https://picsum.photos/1920/1080",
                        contentDescription = null,
                        Modifier
                            .fillMaxWidth()
                            .zIndex(-1f)
                            .graphicsLayer { alpha = 0.50f }
                            .drawWithContent {
                                val colors = listOf(
                                    Color.Black,
                                    Color.Transparent,
                                    Color.Black
                                )
                                drawContent()
                                drawRect(
                                    brush = Brush.verticalGradient(colors),
                                    blendMode = BlendMode.Darken
                                )
                            },
                        placeholder = ColorPainter(
                            Color.Black.copy(alpha = 0.5f)
                        ),
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            Modifier.widthIn(50.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Done, contentDescription = null)
                            Text(text = "My List")
                        }
                        Row(
                            Modifier
                                .widthIn(50.dp)
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.PlayArrow, contentDescription = null,
                                tint = Color.Black
                            )
                            Text(
                                text = "Trailer",
                                Modifier.padding(4.dp),
                                style = TextStyle.Default.copy(
                                    color = Color.Black
                                ),
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
            }
            items(3) {
                MovieSection(LoremIpsum(2).values.first())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AnyFlixTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen()
        }
    }
}

@Composable
fun MovieSection(title: String) {
    Text(
        text = title,
        Modifier.padding(8.dp),
        style = TextStyle.Default.copy(
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(5) {
            Box {
                AsyncImage(
                    model = "https://picsum.photos/1920/1080", contentDescription = null,
                    Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    placeholder = ColorPainter(Color.Black.copy(alpha = 0.9f)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
