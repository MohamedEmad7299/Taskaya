package com.ug.taskaya.ui.sign_in_screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
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
import com.ug.taskaya.ui.composables.OutlinedButtonTaskaya
import com.ug.taskaya.ui.composables.OutlinedPasswordFieldTaskaya
import com.ug.taskaya.ui.composables.OutlinedTextFieldTaskaya
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.AuthState
import com.ug.taskaya.utils.Screen
import com.ug.taskaya.utils.isInternetConnected
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
    facebookSignInAuth: FacebookSignInAuth
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
        SignInContent(
            screenState = screenState,
            signIn = viewModel::signIn,
            onEmailChange = viewModel::onChangeEmail,
            onPasswordChange = viewModel::onChangePassword,
            navigateToSignUp = { navController.navigate(Screen.SignUpScreen.route) },
            facebookSignInAuth = facebookSignInAuth,
            navigateToResetPasswordScreen = { navController.navigate(Screen.ResetPasswordScreen.route) },
            onInternetError = viewModel::onInternetError
        )
    }
}



@Composable
fun SignInContent(
    onInternetError: () -> Unit,
    screenState: SignInState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    signIn: (String, String) -> Unit,
    navigateToSignUp: () -> Unit,
    facebookSignInAuth: FacebookSignInAuth,
    navigateToResetPasswordScreen: () -> Unit
){


    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val googleSignInAuth = GoogleSignInAuth(context)

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){


        val (logo,email,pass,forgetText,loginButton,orText,googleButton,faceButton, signUpText) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 64.dp)
                .size(200.dp)
        )

        OutlinedTextFieldTaskaya(
            modifier = Modifier.constrainAs(email) {
                top.linkTo(logo.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Email",
            value = screenState.email,
            onValueChange = {onEmailChange(it)},
            trailingIconId = R.drawable.mage_email,
            authState = screenState.authState
        )


        OutlinedPasswordFieldTaskaya(
            modifier = Modifier.constrainAs(pass) {
                top.linkTo(email.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Password",
            value = screenState.password,
            onValueChange = { onPasswordChange(it) },
            authState = screenState.authState
        )

        Text(
            text = AnnotatedString("Forget Password ?"),
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ){
                    navigateToResetPasswordScreen()
                }
                .constrainAs(forgetText) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(pass.bottom, 8.dp)
                },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Ment
            )
        )

        ButtonTaskaya(
            modifier = Modifier.constrainAs(loginButton) {
                top.linkTo(forgetText.bottom, 32.dp)
            },
            onClick = {
                signIn(screenState.email,screenState.password)
            },
            label = "Login",
            isLoading = screenState.authState == AuthState.Loading)

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(orText) {
                    top.linkTo(loginButton.bottom, 8.dp)
                },
            painter = painterResource(id = R.drawable.or),
            contentDescription = "")

        OutlinedButtonTaskaya(
            modifier = Modifier
                .constrainAs(googleButton) {
                    top.linkTo(orText.bottom, 16.dp)
                },
            onClick = {

                if (isInternetConnected(context))
                    coroutineScope.launch {

                        googleSignInAuth.signIn()
                    }
                else
                    onInternetError()
            },
            label = "Continue With Google",
            iconId = R.drawable.google
        )

        OutlinedButtonTaskaya(
            modifier = Modifier
                .constrainAs(faceButton){
                    top.linkTo(googleButton.bottom,16.dp)
                },
            onClick = {
                coroutineScope.launch {
                    facebookSignInAuth.registerFacebookCallback(
                        onSuccess = { token ->
                            facebookSignInAuth.handleFacebookAccessToken(token) { _ , message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        },
                        onError = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                    facebookSignInAuth.login(context as Activity)
                }
            }
            ,
            label = "Continue With Facebook",
            iconId = R.drawable.face
        )

        Row(
            Modifier.constrainAs(signUpText){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(faceButton.bottom,16.dp)
            }
        ){
            Text(
                text = "Don’t have an account ? ",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF777777),
                    textAlign = TextAlign.Center,
                )
            )

            Text(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ){
                    navigateToSignUp()
                },
                text = "SignUp",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500),
                    color = Ment,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}