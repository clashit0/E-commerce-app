package com.abhinav.shoppingapp.presentation.screens

import android.R
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.abhinav.shoppingapp.domain.models.UserData
import com.abhinav.shoppingapp.domain.models.UserDataParent
import com.abhinav.shoppingapp.presentation.Utils.LogOutAlertDialog
import com.abhinav.shoppingapp.presentation.navigation.SubNavigation
import com.abhinav.shoppingapp.presentation.viewModels.ShoppingAppViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreenUI(
    navController: NavController,
    firebaseAuth: FirebaseAuth,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getUserId(firebaseAuth.currentUser!!.uid)
    }

    val profileScreenState = viewModel.profileScreenState.collectAsStateWithLifecycle()
    val updateScreenState = viewModel.updateScreenState.collectAsStateWithLifecycle()
    val userProfileImageState = viewModel.updateUserProfileImageState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }
    val isEditing = remember { mutableStateOf(false) }
    val imageUri = remember { mutableStateOf("") }

    val firstName = remember { mutableStateOf(profileScreenState.value.userData?.userData?.firstName ?: "") }
    val lastName = remember { mutableStateOf(profileScreenState.value.userData?.userData?.lastName ?: "") }
    val email = remember { mutableStateOf(profileScreenState.value.userData?.userData?.email ?: "") }
    val phoneNumber = remember { mutableStateOf(profileScreenState.value.userData?.userData?.phoneNumber ?: "") }
    val address = remember { mutableStateOf(profileScreenState.value.userData?.userData?.address ?: "") }

    LaunchedEffect(profileScreenState.value.userData) {
        profileScreenState.value.userData?.userData?.let { userData ->
            firstName.value = userData.firstName
            lastName.value = userData.lastName
            email.value = userData.email
            phoneNumber.value = userData.phoneNumber
            address.value = userData.address
            imageUri.value = userData.profileImage
        }
    }

    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            viewModel.upLoadUserProfileImage(uri)
            imageUri.value = uri.toString()
        }
    }

    // Handle update states
    if (updateScreenState.value.userData != null) {
        Toast.makeText(context, updateScreenState.value.userData, Toast.LENGTH_SHORT).show()
    } else if (updateScreenState.value.errorMessage != null) {
        Toast.makeText(context, updateScreenState.value.errorMessage, Toast.LENGTH_SHORT).show()
    }

    if (userProfileImageState.value.userData != null) {
        imageUri.value = userProfileImageState.value.userData.toString()
    } else if (userProfileImageState.value.errorMessage != null) {
        Toast.makeText(context, userProfileImageState.value.errorMessage, Toast.LENGTH_SHORT).show()
    }

    // Loading states
    if (profileScreenState.value.isLoading || updateScreenState.value.isLoading || userProfileImageState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        return
    }

    if (profileScreenState.value.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = profileScreenState.value.errorMessage!!,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }

    if (profileScreenState.value.userData != null) {
        Scaffold(
            topBar = { }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .background(Color.White) // Optional: Add background color
            ) {
                // Profile Image Section - aligned to start
                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 24.dp)
                ) {
                    Box(
                        modifier = Modifier.size(120.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = if (isEditing.value) imageUri.value else imageUri.value,
                            contentDescription = "Profile picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    color = colorResource(com.abhinav.shoppingapp.R.color.orange), // Use your app's color
                                    CircleShape
                                )
                        ) {
                            when (painter.state) {
                                is AsyncImagePainter.State.Loading -> {
                                    CircularProgressIndicator()
                                }
                                is AsyncImagePainter.State.Error -> {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                                else -> {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        }

                        if (isEditing.value) {
                            IconButton(
                                onClick = {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                },
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Change Picture",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }

                // Form Fields - these will flow naturally without centering
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        modifier = Modifier.weight(1f),
                        readOnly = !isEditing.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange),
                            focusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("First Name") }
                    )

                    OutlinedTextField(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        modifier = Modifier.weight(1f),
                        readOnly = !isEditing.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange),
                            focusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Last Name") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = !isEditing.value,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange),
                        focusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Email") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = phoneNumber.value,
                    onValueChange = { phoneNumber.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = !isEditing.value,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange),
                        focusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Phone Number") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = !isEditing.value,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange),
                        focusedBorderColor = colorResource(com.abhinav.shoppingapp.R.color.orange)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Address") }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons Section
                OutlinedButton(
                    onClick = {
                        showDialog.value = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(com.abhinav.shoppingapp.R.color.orange))
                ) {
                    Text("Log Out", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!isEditing.value) {
                    OutlinedButton(
                        onClick = { isEditing.value = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colorResource(com.abhinav.shoppingapp.R.color.orange)
                        )
                    ) {
                        Text("Edit Profile")
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            val updateUserData = UserData(
                                firstName = firstName.value,
                                lastName = lastName.value,
                                email = email.value,
                                phoneNumber = phoneNumber.value,
                                address = address.value,
                                profileImage = imageUri.value
                            )
                            val userDataParent = UserDataParent(
                                nodeId = profileScreenState.value.userData!!.nodeId,
                                userData = updateUserData
                            )
                            viewModel.updateUserData(userDataParent)
                            isEditing.value = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(com.abhinav.shoppingapp.R.color.orange))
                    ) {
                        Text("Save Profile", color = Color.White)
                    }
                }

                // Dialog at the end
                if (showDialog.value) {
                    LogOutAlertDialog(
                        onDismiss = { showDialog.value = false },
                        onConfirm = {
                            firebaseAuth.signOut()
                            navController.navigate(SubNavigation.LoginSignUpScreen.route)
                        }
                    )
                }
            }
        }
    }
}