package br.com.alura.anyflix.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import br.com.alura.anyflix.ui.theme.AnyFlixTheme

sealed class BottomAppBarItem(val icon: ImageVector) {
    object Search : BottomAppBarItem(Icons.Default.Search)
    object Download : BottomAppBarItem(Icons.Default.Download)
    object Play : BottomAppBarItem(Icons.Default.PlayArrow)
    object Menu : BottomAppBarItem(Icons.Default.Menu)
}

@Composable
fun AnyflixBottomAppBar(
    modifier: Modifier = Modifier,
    items: List<BottomAppBarItem> = listOf(
        BottomAppBarItem.Search,
        BottomAppBarItem.Download,
        BottomAppBarItem.Play,
        BottomAppBarItem.Menu
    ),
    onItemClick: (BottomAppBarItem) -> Unit = {},
) {
    BottomAppBar(
        modifier
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
            },
        containerColor = Color.Transparent,
    ) {
        items.forEach {
            NavigationBarItem(
                selected = false,
                onClick = {
                    onItemClick(it)
                },
                icon = {
                    Icon(
                        it.icon, contentDescription = null,
                        tint = Color.White
                    )
                }
            )
        }
    }

}

@Preview
@Composable
fun AnyflixBottomAppBarPreview() {
    AnyFlixTheme {
        Surface {
            AnyflixBottomAppBar()
        }
    }
}