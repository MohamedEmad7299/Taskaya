package com.ug.taskaya.ui.sign_up_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.ButtonTaskaya
import com.ug.taskaya.ui.composables.OutlinedButtonTaskaya
import com.ug.taskaya.ui.composables.OutlinedPasswordFieldTaskaya
import com.ug.taskaya.ui.composables.OutlinedTextFieldTaskaya
import com.ug.taskaya.ui.theme.Ment


@Composable
fun SignUpScreen(navController: NavController){

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ){
        SignUpContent()
    }

}


@Preview
@Composable
fun SignUpContent() {

//    ConstraintLayout(
//        modifier = Modifier
//            .background(Color.White)
//            .fillMaxSize()
//    ) {
//
//        val (logo,name,email,pass,confirmPass,loginButton,orText,googleButton,faceButton,alreadyText) = createRefs()
//
//        Image(
//            painter = painterResource(id = R.drawable.logo),
//            contentDescription = "",
//            modifier = Modifier
//                .constrainAs(logo) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                .padding(horizontal = 64.dp)
//                .size(200.dp)
//        )
//
//        var nameValue by remember { mutableStateOf("") }
//
//        OutlinedTextFieldTaskaya(
//            modifier = Modifier.constrainAs(name) {
//                top.linkTo(logo.top,156.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            },
//            label = "Name",
//            value = nameValue,
//            trailingIconId = R.drawable.pen,
//            onValueChange = {nameValue = it})
//
//
//        var emailValue by remember { mutableStateOf("") }
//
//        OutlinedTextFieldTaskaya(
//            modifier = Modifier.constrainAs(email) {
//                top.linkTo(name.bottom, 16.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            },
//            label = "Email",
//            value = emailValue,
//            trailingIconId = R.drawable.mage_email,
//            onValueChange = {emailValue = it})
//
//
//        var passValue by remember { mutableStateOf("") }
//
//        OutlinedPasswordFieldTaskaya(
//            modifier = Modifier.constrainAs(pass) {
//                top.linkTo(email.bottom, 16.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            },
//            label = "Password",
//            value = passValue,
//            onValueChange = { passValue = it })
//
//        var repassValue by remember { mutableStateOf("") }
//
//        OutlinedPasswordFieldTaskaya(
//            modifier = Modifier.constrainAs(confirmPass) {
//                top.linkTo(pass.bottom, 16.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            },
//            label = "Confirm Password",
//            value = repassValue,
//            onValueChange = {repassValue = it})
//
//        ButtonTaskaya(
//            modifier = Modifier.constrainAs(loginButton) {
//                top.linkTo(confirmPass.bottom, 32.dp)
//            },
//            onClick = { /*TODO*/ },
//            label = "Sign Up")
//
//        Image(
//            modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(orText) {
//                    top.linkTo(loginButton.bottom, 8.dp)
//                },
//            painter = painterResource(id = R.drawable.or),
//            contentDescription = "")
//
//        OutlinedButtonTaskaya(
//            modifier = Modifier
//                .constrainAs(googleButton) {
//                    top.linkTo(orText.bottom, 16.dp)
//                },
//            onClick = { /*TODO*/ },
//            label = "Continue With Google",
//            iconId = R.drawable.google
//        )
//
//        OutlinedButtonTaskaya(
//            modifier = Modifier
//                .constrainAs(faceButton){
//                    top.linkTo(googleButton.bottom,16.dp)
//                },
//            onClick = { /*TODO*/ },
//            label = "Continue With Facebook",
//            iconId = R.drawable.face
//        )
//
//        Row(
//            Modifier.constrainAs(alreadyText){
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//                top.linkTo(faceButton.bottom,16.dp)
//            }
//        ){
//            Text(
//                text = "Already have an account ? ",
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    fontFamily = FontFamily(Font(R.font.inter)),
//                    fontWeight = FontWeight(400),
//                    color = Color(0xFF777777),
//                    textAlign = TextAlign.Center,
//                )
//            )
//
//            Text(
//                modifier = Modifier.clickable {
//
//                },
//                text = "Login",
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    lineHeight = 16.sp,
//                    fontFamily = FontFamily(Font(R.font.inter)),
//                    fontWeight = FontWeight(500),
//                    color = Ment,
//                    textAlign = TextAlign.Center,
//                )
//            )
//        }
//    }

}
