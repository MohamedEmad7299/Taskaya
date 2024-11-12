package com.ug.taskaya.ui.sign_in_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
fun SignInScreen(navController: NavController){

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ){
        SignInContent()
    }

}


@Preview
@Composable
fun SignInContent(){

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){


        val (logo,email,pass,forgetText,loginButton,orText,googleButton,faceButton) = createRefs()

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

        var emailValue by remember { mutableStateOf("") }

        OutlinedTextFieldTaskaya(
            modifier = Modifier.constrainAs(email) {
                top.linkTo(logo.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Email",
            value = emailValue,
            onValueChange = {emailValue = it},
            trailingIconId = R.drawable.mage_email)


        var passValue by remember { mutableStateOf("") }

        OutlinedPasswordFieldTaskaya(
            modifier = Modifier.constrainAs(pass) {
                top.linkTo(email.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Password",
            value = passValue,
            onValueChange = { passValue = it })

        Text(
            text = AnnotatedString("Forget Password ?"),
            modifier = Modifier
                .clickable { }
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
            onClick = { /*TODO*/ },
            label = "Login")

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
            onClick = { /*TODO*/ },
            label = "Continue With Google",
            iconId = R.drawable.google
        )

        OutlinedButtonTaskaya(
            modifier = Modifier
                .constrainAs(faceButton){
                    top.linkTo(googleButton.bottom,16.dp)
                },
            onClick = { /*TODO*/ },
            label = "Continue With Facebook",
            iconId = R.drawable.face
        )
    }
}