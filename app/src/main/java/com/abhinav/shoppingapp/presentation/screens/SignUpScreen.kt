package com.abhinav.shoppingapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.abhinav.shoppingapp.R
import com.abhinav.shoppingapp.domain.models.UserData
import com.abhinav.shoppingapp.presentation.Utils.CustomTextField
import com.abhinav.shoppingapp.presentation.Utils.SuccessAlertDialog
import com.abhinav.shoppingapp.presentation.navigation.Routes
import com.abhinav.shoppingapp.presentation.navigation.SubNavigation
import com.abhinav.shoppingapp.presentation.viewModels.ShoppingAppViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {

    val state = viewModel.signUpScreenState.collectAsStateWithLifecycle()



    if (state.value.isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align (Alignment.Center ))
        }
    }else if(state.value.errorMessage != null){
        Box(Modifier.fillMaxSize()){
            Text(text = state.value.errorMessage.toString())
        }
    }else if (state.value.userData != null){
        SuccessAlertDialog(
            onClick = {
                navController.navigate(SubNavigation.MainHomeScreen.route)
            }
        )
        return
    }else if (state.value.errorMessage != null) {
        // Don't return here - just show the error message above the form
        Text(
            text = "Error: ${state.value.errorMessage}",
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
    else{
        val context = LocalContext.current
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                "SignUp",
                fontSize = 24.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Start)
            )
            CustomTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = "First Name",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            CustomTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = "Last Name",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            CustomTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },
                label = "Phone Number",
                leadingIcon = Icons.Default.Phone,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            CustomTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            CustomTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                label = "Confirm Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange)),
                onClick = {
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@Button
                    } else {
                        val userData = UserData(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            password = password,
                            phoneNumber = phoneNumber
                        )
                        viewModel.createUser(
                            userData = userData
                        )
                        Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                    }
                }) {
                Text(text = "Sign Up", color = colorResource(id = R.color.white))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account?")
                TextButton(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Login",
                        color = colorResource(id = R.color.orange))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ){
                Image(painter = painterResource(id = R.drawable.google), contentDescription = null,
                    modifier = Modifier.size(24.dp))

                Spacer(Modifier.size(8.dp))

                Text(text = "Login with google")
            }



        }
    }


}