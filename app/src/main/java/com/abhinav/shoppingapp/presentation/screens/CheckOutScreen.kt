package com.abhinav.shoppingapp.presentation.screens

import android.R
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
import com.abhinav.shoppingapp.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    productId: String,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {

    val state = viewModel.getProductByIdState.collectAsStateWithLifecycle()
    val productData = state.value.userData

    val email = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val selectedMethod = remember { mutableStateOf("Standart FREE delivery over 999") }

    LaunchedEffect(key1 = Unit) {
        viewModel.getProductById(productId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Shipping")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "back")
                    }
                }


            )
        }
    ) { innerPadding ->
        when {
            state.value.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(Modifier.padding(8.dp))
                    Text(text = "Sorry unable to get information")
                }
            }

            state.value.userData == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(Modifier.padding(8.dp))
                    Text(text = "No products available")
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = state.value.userData!!.image,
                            contentDescription = null,

                            modifier = Modifier
                                .size(80.dp)
                                .border(1.dp, color = Color.Gray)


                        )

                        Spacer(Modifier.width(16.dp))

                        Column {
                            Text(
                                text = state.value.userData!!.name,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                text = state.value.userData!!.price,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )

                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Column {
                        Text(
                            "Contact Information",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Column {
                        Text(
                            "Shipping address",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = country.value,
                            onValueChange = { country.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Country") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        Spacer(Modifier.height(8.dp))

                        Row {
                            OutlinedTextField(
                                value = firstName.value,
                                onValueChange = { firstName.value = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("First Name") },

                                )

                            OutlinedTextField(
                                value = lastName.value,
                                onValueChange = { lastName.value = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Last Name") },

                                )


                        }

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = address.value,
                            onValueChange = { address.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Address") },

                            )

                        Spacer(Modifier.height(8.dp))

                        Row {
                            OutlinedTextField(
                                value = city.value,
                                onValueChange = { city.value = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("City") },

                                )

                            OutlinedTextField(
                                value = postalCode.value,
                                onValueChange = { postalCode.value = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Postal Code") },

                                )


                        }


                    }

                    Spacer(Modifier.height(16.dp))

                    Column {
                        Text(
                            "Shipping Method",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(Modifier.height(16.dp))


                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedMethod.value == "Standard Free Delivery over Rs 999",
                                onClick = {
                                    selectedMethod.value == "Standard Free delivery over Rs 999"
                                })

                            Spacer(Modifier.height(16.dp))

                            Text("Standard Free delivery over Rs 999")


                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedMethod.value == "Cash on delivery Rs 50",
                                onClick = {
                                    selectedMethod.value == "Cash on delivery Rs 50"
                                })

                            Spacer(Modifier.height(16.dp))

                            Text("Cash on delivery Rs 50")


                        }

                    }

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            //pay.invoke
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.holo_orange_dark))
                    ) {
                        Text("Continue to Shipping")
                    }
                }
            }

        }

    }
}