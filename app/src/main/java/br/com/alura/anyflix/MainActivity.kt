package br.com.alura.anyflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.alura.anyflix.navigation.AnyflixNavHost
import br.com.alura.anyflix.navigation.homeRoute
import br.com.alura.anyflix.navigation.movieDetailsRoute
import br.com.alura.anyflix.navigation.movieDetailsRouteFullpath
import br.com.alura.anyflix.navigation.myListRoute
import br.com.alura.anyflix.navigation.navigateToHome
import br.com.alura.anyflix.navigation.navigateToMyList
import br.com.alura.anyflix.ui.components.AnyflixBottomAppBar
import br.com.alura.anyflix.ui.components.BottomAppBarItem
import br.com.alura.anyflix.ui.theme.AnyFlixTheme
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnyFlixTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val currentBackStack by navController.currentBackStackEntryAsState()
                    val currentDestination = currentBackStack?.destination
                    val currentRoute = currentDestination?.route
                    AnyflixApp(
                        currentRoute = currentRoute,
                        onBottomAppBarItemSelected = { item ->

                            when (item) {
                                BottomAppBarItem.Home -> {
                                    navController.navigateToHome(
                                        navOptions {
                                            launchSingleTop = true
                                            popUpTo(homeRoute)
                                        }

                                    )
                                }

                                BottomAppBarItem.MyList -> {
                                    navController.navigateToMyList(
                                        navOptions {
                                            launchSingleTop = true
                                            popUpTo(myListRoute)
                                        }
                                    )
                                }
                            }
                        },
                        onBackNavigationClick = {
                            navController.popBackStack()
                        }
                    ) {
                        AnyflixNavHost(navController = navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnyflixApp(
    currentRoute: String? = null,
    onBottomAppBarItemSelected: (BottomAppBarItem) -> Unit,
    onBackNavigationClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val bottomAppBarItems = remember {
        listOf(
            BottomAppBarItem.Home,
            BottomAppBarItem.MyList
        )
    }
    val selectedBottomAppBarItem = when (currentRoute) {
        homeRoute -> BottomAppBarItem.Home
        myListRoute -> BottomAppBarItem.MyList
        else -> BottomAppBarItem.Home
    }
    val isShowBackNavigation = when (currentRoute) {
        myListRoute, movieDetailsRouteFullpath -> true
        else -> false
    }
    val isShowBottomAppBar = when (currentRoute) {
        homeRoute, myListRoute -> true
        else -> false
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (currentRoute) {
                        myListRoute -> {
                            Text("Minha lista")
                        }

                        movieDetailsRouteFullpath -> {
                            Text("Informações")
                        }

                        homeRoute -> {
                            Image(
                                painterResource(id = R.drawable.anyflix),
                                contentDescription = null,
                                Modifier.size(30.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                navigationIcon = {
                    if (isShowBackNavigation) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            Modifier
                                .padding(16.dp)
                                .clickable {
                                    onBackNavigationClick()
                                }
                        )
                    }
                },
                actions = {
                    Row(
                        Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            Modifier
                                .size(24.dp)
                        )
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            Modifier
                                .size(24.dp)
                        )
                    }

                })
        },
        bottomBar = {
            if (isShowBottomAppBar) {
                AnyflixBottomAppBar(
                    selectedItem = selectedBottomAppBarItem,
                    items = bottomAppBarItems,
                    onItemClick = {
                        onBottomAppBarItemSelected(it)
                    }
                )
            }
        }
    ) {
        Box(
            Modifier.padding(it)
        ) {
            content()
        }
    }
}


@Preview
@Composable
fun AnyflixAppWithHomeRoutePreview() {
    AnyFlixTheme {
        Surface {
            AnyflixApp(currentRoute = homeRoute,
                onBottomAppBarItemSelected = {

                }
            ) {

            }
        }
    }
}

@Preview
@Composable
fun AnyflixAppPreview() {
    AnyFlixTheme {
        Surface {
            AnyflixApp(
                onBottomAppBarItemSelected = {

                }
            ) {

            }
        }
    }
}

@Preview
@Composable
fun AnyflixAppWithNormalTopAppBarPreview() {
    AnyFlixTheme {
        Surface {
            AnyflixApp(
                currentRoute = myListRoute,
                onBottomAppBarItemSelected = {

                },
            ) {

            }
        }
    }
}
