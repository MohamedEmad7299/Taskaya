package com.ug.taskaya.ui.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ug.taskaya.R
import com.ug.taskaya.utils.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController : NavController,
   // viewModel: SplashScreenViewModel = hiltViewModel()
){

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)

    val context = LocalContext.current
    val scale = remember{

        Animatable(3f)
    }

    LaunchedEffect(key1 = true){

        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )

        delay(1000)

//        val nextDestination =
//            if (SharedPreferences.loggedEmail == null)
//                Screen.SignInScreen.route
//            else
//                Screen.HomeScreen.route

//        if (isInternetConnected(context)){
//
//            navController.navigate(nextDestination){
//                popUpTo(navController.graph.id){
//                    inclusive = true
//                }
//            }
//        }
//
//        else navController.navigate(Screen.NoInternetScreen.route)

        navController.navigate(Screen.OnboardingScreen.route){

            popUpTo(navController.graph.id){
                inclusive = true
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        val logo = createRef()

        Image(

            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end) }
                .padding(horizontal = 64.dp)
                .scale(scale.value)
                .size(200.dp)
        )
    }
}