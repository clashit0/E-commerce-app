package com.abhinav.shoppingapp.presentation.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.abhinav.shoppingapp.R
import com.abhinav.shoppingapp.domain.models.CartDataModels
import com.abhinav.shoppingapp.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    productId: String? = null,                         // <-- CHANGED: optional productId
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    pay: () -> Unit
) {
    // State for single-product lookup (keeps original behaviour)
    val stateSingle = viewModel.getProductByIdState.collectAsStateWithLifecycle()

    // State for cart (for multi-product checkout)
    val cartState = viewModel.getCartState.collectAsStateWithLifecycle()
    val cartItems: List<CartDataModels?> = cartState.value.userData

    // Form fields
    val email = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val selectedMethod = remember { mutableStateOf("Standard FREE delivery over 999") }

    // If we have a productId (Buy now flow) -> fetch single product
    LaunchedEffect(key1 = productId) {
        if (!productId.isNullOrEmpty()) {
            viewModel.getProductById(productId)
        } else {
            // If no productId -> this is the Cart -> load cart items
            viewModel.getCart()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shipping") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) { innerPadding ->
        // 1) If single-product flow (productId provided)
        if (!productId.isNullOrEmpty()) {
            // Keep the original single-product UI (with safety checks)
            when {
                stateSingle.value.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                stateSingle.value.errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Sorry unable to get information")
                    }
                }

                stateSingle.value.userData == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No product available")
                    }
                }

                else -> {
                    // show the single product and the checkout form (same as before)
                    val product = stateSingle.value.userData!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = product.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .border(1.dp, color = Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                                Text(text = product.price, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // --- Contact & shipping form (same fields as before) ---
                        Column {
                            Text("Contact Information", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = email.value, onValueChange = { email.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Shipping address", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = country.value, onValueChange = { country.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Country") })
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                OutlinedTextField(value = firstName.value, onValueChange = { firstName.value = it }, modifier = Modifier.weight(1f), label = { Text("First Name") })
                                OutlinedTextField(value = lastName.value, onValueChange = { lastName.value = it }, modifier = Modifier.weight(1f), label = { Text("Last Name") })
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = address.value, onValueChange = { address.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Address") })
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                OutlinedTextField(value = city.value, onValueChange = { city.value = it }, modifier = Modifier.weight(1f), label = { Text("City") })
                                OutlinedTextField(value = postalCode.value, onValueChange = { postalCode.value = it }, modifier = Modifier.weight(1f), label = { Text("Postal Code") })
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column {
                            Text("Shipping Method", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = selectedMethod.value == "Standard FREE delivery over 999", onClick = { selectedMethod.value = "Standard FREE delivery over 999" })
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Standard FREE delivery over Rs 999")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = selectedMethod.value == "Cash on delivery Rs 50", onClick = { selectedMethod.value = "Cash on delivery Rs 50" })
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Cash on delivery Rs 50")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { pay.invoke() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange))) {
                            Text("Continue to Shipping")
                        }
                    }
                }
            }
        } else {
            // 2) Cart -> Checkout flow (show all cart items + shipping form)
            when {
                cartState.value.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                cartState.value.errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Unable to load cart: ${cartState.value.errorMessage}")
                    }
                }

                cartItems.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No items in cart")
                    }
                }

                else -> {
                    val nonNullCart = cartItems.filterNotNull()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Show heading and list of cart items
                        Text("Your items", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(12.dp))
                        nonNullCart.forEach { cartItem ->
                            // Reuse CartItemCard visual; if you want a compact variant, you can create a compact card
                            CartItemCard(item = cartItem)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // --- Contact & shipping form below (same as single-product) ---
                        Column(modifier = Modifier.padding(12.dp)) {
                            OutlinedTextField(value = email.value, onValueChange = { email.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = country.value, onValueChange = { country.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Country") })
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                OutlinedTextField(value = firstName.value, onValueChange = { firstName.value = it }, modifier = Modifier.weight(1f), label = { Text("First Name") })
                                OutlinedTextField(value = lastName.value, onValueChange = { lastName.value = it }, modifier = Modifier.weight(1f), label = { Text("Last Name") })
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = address.value, onValueChange = { address.value = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Address") })
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                OutlinedTextField(value = city.value, onValueChange = { city.value = it }, modifier = Modifier.weight(1f), label = { Text("City") })
                                OutlinedTextField(value = postalCode.value, onValueChange = { postalCode.value = it }, modifier = Modifier.weight(1f), label = { Text("Postal Code") })
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(selected = selectedMethod.value == "Standard FREE delivery over 999", onClick = { selectedMethod.value = "Standard FREE delivery over 999" })
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Standard FREE delivery over Rs 999")
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(selected = selectedMethod.value == "Cash on delivery Rs 50", onClick = { selectedMethod.value = "Cash on delivery Rs 50" })
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Cash on delivery Rs 50")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(onClick = { pay.invoke() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange))) {
                                Text("Continue to Shipping")
                            }
                        }
                    }
                }
            }
        }
    }
}
