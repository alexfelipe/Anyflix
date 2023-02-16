package br.com.alura.anyflix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.anyflix.ui.theme.AnyFlixTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnyflixTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onProfileMenuClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Box(
                modifier =
                Modifier
                    .background(
                        Color.Red,
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = title,
                    Modifier
                        .padding(
                            vertical = 4.dp,
                            horizontal = 16.dp
                        )
                        .align(Alignment.Center),
                )
            }
        },
        modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = onProfileMenuClick) {
                Icon(
                    Icons.Default.PersonOutline,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
fun AnyflixTopAppBarPreview() {
    AnyFlixTheme {
        Surface {
            AnyflixTopAppBar(title = "Anyflix")
        }
    }
}