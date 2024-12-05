package com.ug.taskaya.ui.reset_password_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.ButtonTaskaya
import com.ug.taskaya.ui.composables.OutlinedTextFieldTaskaya
import com.ug.taskaya.ui.theme.DarkGray
import com.ug.taskaya.utils.AuthState

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel(),
){

    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    if (screenState.message.isNotEmpty()){
        LaunchedEffect(key1 = screenState.launchedEffectKey){
            Toast.makeText(context, screenState.message, Toast.LENGTH_SHORT).show()
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ){
        ResetPasswordContent(
            screenState = screenState,
            onEmailChange = viewModel::onChangeEmail,
            forgetPasswordRequest = viewModel::sendPasswordResetEmail,
            backToLogin = {
                navController.popBackStack()
            }
        )
    }
}


@Composable
fun ResetPasswordContent(
    screenState: ResetPasswordState,
    onEmailChange: (String) -> Unit,
    forgetPasswordRequest: (String) -> Unit,
    backToLogin: () -> Unit,
){
    
    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){


        val (logo,forgetText,instructions,email,emailField,sendButton,backButton) = createRefs()

        IconButton(
            modifier = Modifier.constrainAs(backButton) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start,16.dp)
            },
            onClick = backToLogin
        ) {

            Icon(
                painter = painterResource(R.drawable.back_arrow),
                tint = DarkGray,
                contentDescription = ""
            )
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top, 64.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 64.dp)
                .size(200.dp)
        )

        Text(
            modifier = Modifier
                .constrainAs(forgetText) {
                    top.linkTo(logo.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Forgot Password?",
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(550),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier
                .constrainAs(instructions) {
                    top.linkTo(forgetText.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "No worries, we’ll send you reset instructions",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF777777),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier
                .constrainAs(email) {
                    top.linkTo(instructions.bottom, 24.dp)
                    start.linkTo(parent.start , 16.dp)
                },
            text = "Email",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(550),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )

        OutlinedTextFieldTaskaya(
            modifier = Modifier.constrainAs(emailField) {
                top.linkTo(email.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Enter your email",
            value = screenState.email,
            onValueChange = { onEmailChange(it) },
            trailingIconId = R.drawable.mage_email,
            authState = screenState.authState
        )

        ButtonTaskaya(
            modifier = Modifier.constrainAs(sendButton) {
                top.linkTo(emailField.bottom, 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isLoading = screenState.authState == AuthState.Loading,
            label = "Send",
            onClick = {
                forgetPasswordRequest(screenState.email)
            }
        )
    }
}