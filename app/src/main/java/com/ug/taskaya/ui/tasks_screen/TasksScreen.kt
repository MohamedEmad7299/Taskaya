package com.ug.taskaya.ui.tasks_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.CategoriesBar
import com.ug.taskaya.ui.theme.Ment

@Composable
fun TasksScreen(navController: NavController){

    TasksContent()
}


@Preview
@Composable
fun TasksContent(){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.composition))

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){

        val(fButton,animation) = createRefs()

        CategoriesBar()

        LottieAnimation(
            modifier = Modifier
                .size(400.dp)
                .constrainAs(animation) {
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            composition = composition,
            isPlaying = true
        )

        FloatingActionButton(
            modifier = Modifier.constrainAs(fButton){
                end.linkTo(parent.end,16.dp)
                bottom.linkTo(parent.bottom,32.dp)
            },
            onClick = { /* Handle click */ },
            backgroundColor = Ment,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
    }

}