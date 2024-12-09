package com.ug.taskaya.ui.sign_up_screen

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
import com.ug.taskaya.ui.sign_in_screen.FacebookSignInAuth
import com.ug.taskaya.ui.sign_in_screen.GoogleSignInAuth
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.AuthState
import com.ug.taskaya.utils.isInternetConnected
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
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
        SignUpContent(
            screenState = screenState,
            facebookSignInAuth = facebookSignInAuth,
            onEmailChange = viewModel::onChangeEmail,
            onPasswordChange = viewModel::onChangePassword,
            onRePasswordChange = viewModel::onChangeRePassword,
            onClickLogin = { navController.popBackStack() },
            signUp = { viewModel.signUp(screenState.email,screenState.password) },
            onNameChange = viewModel::onChangeName,
            onInternetError = viewModel::onInternetError
        )
    }
}



@Composable
fun SignUpContent(
    onInternetError: () -> Unit,
    screenState: SignUpState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRePasswordChange: (String) -> Unit,
    signUp: () -> Unit,
    facebookSignInAuth: FacebookSignInAuth,
    onClickLogin: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val googleSignInAuth = GoogleSignInAuth(context)

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        val (logo,name,email,pass,confirmPass,loginButton,orText,googleButton,faceButton,alreadyText) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 64.dp)
                .size(200.dp)
        )


        OutlinedTextFieldTaskaya(
            modifier = Modifier.constrainAs(name) {
                top.linkTo(logo.top,156.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Name",
            value = screenState.name,
            trailingIconId = R.drawable.pen,
            onValueChange = { onNameChange(it) },
            authState = screenState.authState
        )


        OutlinedTextFieldTaskaya(
            modifier = Modifier.constrainAs(email) {
                top.linkTo(name.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Email",
            value = screenState.email,
            trailingIconId = R.drawable.mage_email,
            onValueChange = { onEmailChange(it) },
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


        OutlinedPasswordFieldTaskaya(
            modifier = Modifier.constrainAs(confirmPass) {
                top.linkTo(pass.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Confirm Password",
            value = screenState.rePassword,
            onValueChange = { onRePasswordChange(it) },
            authState = screenState.authState
        )

        ButtonTaskaya(
            modifier = Modifier.constrainAs(loginButton) {
                top.linkTo(confirmPass.bottom, 32.dp)
            },
            onClick = signUp,
            isLoading = (screenState.authState == AuthState.Loading),
            label = "Sign Up")

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
            Modifier.constrainAs(alreadyText){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(faceButton.bottom,16.dp)
            }
        ){
            Text(
                text = "Already have an account ? ",
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
                    onClickLogin()
                },
                text = "Login",
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
