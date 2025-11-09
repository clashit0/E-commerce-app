package com.abhinav.shoppingapp.presentation.Utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhinav.shoppingapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessAlertDialog(onClick: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier = Modifier.background(shape = RoundedCornerShape(16.dp), color = Color.White),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(color = colorResource(R.color.orange), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "Success",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.orange)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Congratulation, you have \n completed you registration!",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = onClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Go to Home",
                            color = Color.Gray
                        )

                    }
                }
            }
        }
    )
}