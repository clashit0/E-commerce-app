package com.abhinav.shoppingapp.presentation.screens

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.abhinav.shoppingapp.domain.models.CartDataModels
import com.abhinav.shoppingapp.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val cartState = viewModel.getCartState.collectAsStateWithLifecycle()
    val cardData = cartState.value.userData
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(Unit) {
        viewModel.getCart()
    }

    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Shopping Cart",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {
            when {
                cartState.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                cartState.value.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Spacer(Modifier.padding(8.dp))
                        Text(text = "Sorry unable to get informaton")
                    }
                }

                cardData.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No products available")
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                "Items",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.weight(.45f))
                            Text(
                                "Details",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.weight(1f))
                            Text(
                                "Quantity",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.weight(.15f))
                        }
                        LazyColumn(
                            Modifier.weight(.6f)
                        ) {

                            val nonNullCartItems = cardData.filterNotNull()
                            items(nonNullCartItems) { cartItem ->
                                CartItemCard(item = cartItem, viewModel = viewModel )
                            }
                        }
                        HorizontalDivider(Modifier.padding(top = 16.dp, bottom = 8.dp))

                        Button(
                            onClick = {
                                navController.navigate("checkout_screen")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.holo_orange_dark))
                        ) {
                            Text("Checkout")
                        }


                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartDataModels,
    viewModel: ShoppingAppViewModel
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = "Size:${item.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )


                Text(
                    text = "Price: Rs${item.price}",
                    style = MaterialTheme.typography.bodyLarge,

                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {

                Row {
                    IconButton(onClick = {
                        val decrease = CartDataModels(
                            productId = item.productId,
                            name = item.name,
                            price = item.price,
                            image = item.image,
                            size = item.size,
                            quantity = -1,
                            description = item.description,
                            category = item.category

                        )
                        viewModel.addToCart(decrease)
                    }) {
                        Text("-",
                            style = MaterialTheme.typography.headlineSmall)
                    }

                    Text(
                        "Qty : ${item.quantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    IconButton(onClick = {
                        val decrease = CartDataModels(
                            productId = item.productId,
                            name = item.name,
                            price = item.price,
                            image = item.image,
                            size = item.size,
                            quantity = +1,
                            description = item.description,
                            category = item.category

                        )
                        viewModel.addToCart(decrease)
                    }) {
                        Text("+",
                            style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }
        }
    }
}