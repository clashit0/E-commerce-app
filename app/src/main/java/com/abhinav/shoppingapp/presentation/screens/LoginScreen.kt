package com.abhinav.shoppingapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abhinav.shoppingapp.R
import com.abhinav.shoppingapp.presentation.Utils.CustomTextField

@Composable
fun LoginScreenUi(
    navController: NavController
){
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 24.dp).align(Alignment.Start),
            style = TextStyle(fontWeight = FontWeight.Bold)
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

        Spacer(Modifier.padding(8.dp))

        CustomTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = "Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.padding(4.dp))

        Text("Forget Password?",modifier = Modifier.align(Alignment.End))

        Spacer(Modifier.padding(4.dp))

        Button(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.orange)),
            onClick = {}
        ) { Text("Login",
            color = colorResource(R.color.white))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text("Don't have an account?")
            TextButton(onClick = {/*navigate to login screen*/}) {
                androidx.compose.material3.Text(
                    "SignUp",
                    color = colorResource(id = R.color.orange)
                )
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
            androidx.compose.material3.Text(
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

            androidx.compose.material3.Text(text = "Login with google")
        }


    }
}