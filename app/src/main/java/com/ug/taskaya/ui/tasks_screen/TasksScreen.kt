package com.ug.taskaya.ui.tasks_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.LabelsBar
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.Screen

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
){

    val screenState by viewModel.screenState.collectAsState()

    TasksContent(
        screenState = screenState,
        onClickAddButton = {
            navController.navigate(Screen.WritingTaskScreen.route)
        }
    )
}


@Composable
fun TasksContent(
    screenState: TasksState,
    onClickAddButton: () -> Unit
){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.task_animation))

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){

        val(floatingButton,animation) = createRefs()

        LabelsBar(
            labels = screenState.labels,
            onClickLabel = {}
        )

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
            modifier = Modifier.constrainAs(floatingButton){
                end.linkTo(parent.end,16.dp)
                bottom.linkTo(parent.bottom,32.dp)
            },
            onClick = onClickAddButton,
            containerColor = Ment,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }
    }
}