package com.abhinav.shoppingapp.presentation.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.abhinav.shoppingapp.domain.models.ProductsDataModels
import com.abhinav.shoppingapp.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllFav(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val getAllFav = viewModel.getAllFavState.collectAsStateWithLifecycle()
    val getFavData: List<ProductsDataModels> = getAllFav.value.userData.orEmpty().filterNotNull()

    LaunchedEffect(Unit) {
        viewModel.getAllFav()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "WishList",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

            )
        }
    ) { innerpadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                placeholder = {Text("Search")},
                leadingIcon = { Icon(Icons.Default.Search,
                    contentDescription = "Search")}
            )
            when{
                getAllFav.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                getAllFav.value.errorMessage != null ->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = getAllFav.value.errorMessage.toString())
                    }
                }
                getFavData.isEmpty() ->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Your wishlist is empty")
                    }
                }
                else ->{
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) { }
                }

            }
        }
    }
}