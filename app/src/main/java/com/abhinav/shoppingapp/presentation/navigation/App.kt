package com.abhinav.shoppingapp.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abhinav.shoppingapp.R
import com.abhinav.shoppingapp.presentation.screens.AllCategoriesScreen
import com.abhinav.shoppingapp.presentation.screens.CartScreen
import com.abhinav.shoppingapp.presentation.screens.CheckoutScreen
import com.abhinav.shoppingapp.presentation.screens.EachCategoriesScreen
import com.abhinav.shoppingapp.presentation.screens.EachProductDetailsScreen
import com.abhinav.shoppingapp.presentation.screens.GetAllFav
import com.abhinav.shoppingapp.presentation.screens.GetAllProducts
import com.abhinav.shoppingapp.presentation.screens.HomeScreenUI
import com.abhinav.shoppingapp.presentation.screens.LoginScreenUi
import com.abhinav.shoppingapp.presentation.screens.ProfileScreenUI
import com.abhinav.shoppingapp.presentation.screens.SignUpScreen
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorDirection
import com.example.bottombar.model.IndicatorStyle
import com.google.firebase.auth.FirebaseAuth

data class BottomNavItem(val name: String, val icon: ImageVector, val unselectedIcon: ImageVector)

@Composable
fun App(
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()

    var selectedItem by remember { mutableStateOf(0) }

    val navBackSTackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackSTackEntry?.destination?.route

    val shouldShowBottomBar = remember { mutableStateOf(false) }

    LaunchedEffect(currentDestination) {
        shouldShowBottomBar.value = when (currentDestination) {
            Routes.LoginScreen.route, Routes.SignUpScreen.route -> false
            else -> true
        }
    }

    val BottomNavItem = listOf(
        BottomNavItem("Home", Icons.Default.Home, unselectedIcon = Icons.Outlined.Home),
        BottomNavItem("Wishlist", Icons.Default.Favorite, unselectedIcon = Icons.Outlined.Favorite),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, unselectedIcon = Icons.Outlined.ShoppingCart),
        BottomNavItem("Profile", Icons.Default.Person, unselectedIcon = Icons.Outlined.Person)
    )

    var startScreen = if (firebaseAuth.currentUser != null) {
        SubNavigation.MainHomeScreen.route
    } else {
        SubNavigation.LoginSignUpScreen.route

    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar.value) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = WindowInsets.navigationBars.asPaddingValues()
                                .calculateBottomPadding()
                        )

                ) {
                    AnimatedBottomBar(
                        selectedItem = selectedItem,
                        itemSize = BottomNavItem.size,
                        containerColor = Color.Transparent,
                        indicatorColor = colorResource(id = R.color.orange),
                        indicatorDirection = IndicatorDirection.BOTTOM,
                        indicatorStyle = IndicatorStyle.FILLED
                    ) {
                        BottomNavItem.forEachIndexed { index, navigationItem ->

                            BottomBarItem(
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    when (index) {
                                        0 -> navController.navigate(Routes.HomeScreen.route)
                                        1 -> navController.navigate(Routes.WishlistScreen.route)
                                        2 -> navController.navigate(Routes.CartScreen.route)
                                        3 -> navController.navigate(Routes.ProfileScreen.route)
                                    }

                                },
                                imageVector = navigationItem.icon,
                                label = navigationItem.name,
                                containerColor = Color.Transparent
                            )
                        }
                    }
                }
            }
        }
    ) {innerPadding->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = if (shouldShowBottomBar.value)60.dp else 0.dp)

        ){
            NavHost(
                navController = navController,
                startDestination = startScreen
            ){
                navigation(
                    route = SubNavigation.LoginSignUpScreen.route,
                    startDestination = Routes.LoginScreen.route
                ){
                    composable(
                        route = Routes.LoginScreen.route
                    ){
                        LoginScreenUi(navController = navController)
                    }

                    composable(
                        route = Routes.SignUpScreen.route
                    ){
                        SignUpScreen(navController)
                    }


                }

                navigation(
                    route = SubNavigation.MainHomeScreen.route,
                    startDestination = Routes.HomeScreen.route
                ){
                    composable(
                        route = Routes.HomeScreen.route
                    ){
                        HomeScreenUI(navController=navController)
                    }

                    composable(
                        route = Routes.ProfileScreen.route
                    ){
                        ProfileScreenUI(navController = navController, firebaseAuth = firebaseAuth)
                    }

                    composable(
                        route = Routes.WishlistScreen.route
                    ){
                        GetAllFav(navController = navController)
                    }

                    composable(
                        route = Routes.CartScreen.route
                    ){
                        CartScreen(navController = navController)
                    }

                    composable(
                        route = Routes.SeeAllProductScreen.route
                    ){
                        GetAllProducts(navController = navController)
                    }

                    composable(
                        route = Routes.AllCategoriesScreen.route
                    ){
                        AllCategoriesScreen(navController = navController)
                    }


                }
                composable(
                    route = "${Routes.EachProductDetailsScreen.route}/{productId}",
                    arguments = listOf(
                        navArgument("productId"){type = NavType.StringType}
                    )
                ){
                    navBackSTackEntry ->
                    val productId = navBackSTackEntry.arguments?.getString("productId") ?: ""
                    EachProductDetailsScreen(productId = productId, navController = navController)

                }

                composable(
                    route = "${Routes.EachCategoryItemScreens.route}/{categoryName}",
                    arguments = listOf(
                        navArgument("categoryName"){type = NavType.StringType}
                    )
                ){
                    navBackSTackEntry ->
                    val categoryName = navBackSTackEntry.arguments?.getString("categoryName")?:""
                    EachCategoriesScreen(navController = navController, categoryName = categoryName)
                }

                composable(
                    route = "${Routes.CheckoutScreen.route}.{prodcutId}",
                    arguments = listOf(
                        navArgument("productId"){
                            type = NavType.StringType
                        }
                    )

                ){
                    navBackSTackEntry ->
                    val productId = navBackSTackEntry.arguments?.getString("productId")?:""
                    CheckoutScreen(productId = productId, navController = navController)

                }



            }
        }


    }

}