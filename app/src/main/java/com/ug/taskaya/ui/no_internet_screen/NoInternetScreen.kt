package com.ug.taskaya.ui.no_internet_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.DarkGray
import com.ug.taskaya.utils.isInternetConnected

@Composable
fun NoInternetScreen(navController: NavController){

    val context = LocalContext.current

    NoInternetContent{

        if (isInternetConnected(context)) navController.popBackStack()
    }
}


@Composable
fun NoInternetContent(
    onClickTryAgain : () -> Unit
){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet_connection))

    val systemUiController = rememberSystemUiController()

    SideEffect {

        systemUiController.setStatusBarColor(Color.White,darkIcons = true)
    }

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){

        val (animation,
            instructionsText,
            tryAgainButton) = createRefs()

        LottieAnimation(
            modifier = Modifier
                .size(250.dp)
                .constrainAs(animation) {
                    top.linkTo(parent.top, 128.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            composition = composition,
            isPlaying = true
        )

        Text(
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(instructionsText){
                top.linkTo(animation.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)},
            text = "Try checking the network cables modem and router",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(300),
                color = DarkGray,
                textAlign = TextAlign.Center,
            )
        )

        Text(
            text = "Try Again",
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ){
                    onClickTryAgain()
                }
                .constrainAs(tryAgainButton) {
                    top.linkTo(instructionsText.bottom,32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color.Black,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

