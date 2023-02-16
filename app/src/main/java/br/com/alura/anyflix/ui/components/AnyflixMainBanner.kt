package br.com.alura.anyflix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.alura.anyflix.model.Movie
import coil.compose.AsyncImage

@Composable
fun AnyflixMainBanner(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
) {
    Box(
        Modifier
            .height(500.dp)
    ) {
        AsyncImage(
            model = movie.image,
            contentDescription = null,
            Modifier
                .fillMaxWidth()
                .drawWithContent {
                    val colors = listOf(
                        Color.Black.copy(0.5f),
                        Color.Transparent,
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.9f),
                    )
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors
                        ),
                    )
                }
                .clickable {
                    onMovieClick(movie)
                },
            placeholder = ColorPainter(
                Color.Gray.copy(alpha = 0.5f)
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
