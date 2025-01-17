package com.ug.taskaya.ui.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.data.fake_data.FakeData
import com.ug.taskaya.ui.composables.TasksBox
import com.ug.taskaya.ui.composables.BarChart
import com.ug.taskaya.ui.composables.PieeChart
import com.ug.taskaya.utils.Screen
import com.ug.taskaya.utils.SharedState

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val screenState by viewModel.screenState.collectAsState()

    ProfileContent(
        screenState = screenState,
        onClickRank = { navController.navigate(Screen.RanksScreen.route) }
    )
}


@Composable
fun ProfileContent(
    screenState : ProfileState,
    onClickRank: () -> Unit
){

    val scrollState = rememberScrollState()

    val tasks by SharedState.tasks.collectAsState()

    val completedTasksCounter = tasks.filter { it.isCompleted }.size
    val pendingTasksCounter = tasks.filter { !it.isCompleted }.size

    val currentRank = FakeData.getRank(tasks.filter { it.isCompleted }.size)

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(Color.White)
            .fillMaxSize()
    ){
        
        ConstraintLayout{

            val (name,rank,overview,
                pendingTasks, barChart, pieChart) = createRefs()

            Text(
                modifier = Modifier
                    .clickable {
                        onClickRank()
                    }.constrainAs(name){
                    top.linkTo(parent.top,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                text = screenState.name,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF090909),
                    textAlign = TextAlign.Center,
                )
            )

            Text(
                modifier = Modifier
                    .clickable {
                        onClickRank()
                    }
                    .constrainAs(rank){
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start,16.dp)
                },
                text = currentRank.second,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = currentRank.third,
                )
            )

            Text(
                modifier = Modifier.constrainAs(overview){
                    top.linkTo(rank.bottom,32.dp)
                    start.linkTo(parent.start,16.dp)
                },
                text = "Tasks Overview",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                )
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(pendingTasks) {
                        top.linkTo(overview.bottom, 16.dp)
                    }
            ){
                TasksBox(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),
                    numberOfTasks = completedTasksCounter
                )

                TasksBox(
                    label = "Pending Tasks",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    numberOfTasks = pendingTasksCounter
                )
            }

            BarChart(
                modifier = Modifier
                    .constrainAs(barChart) {
                        top.linkTo(pendingTasks.bottom)
                    }
            )

            PieeChart(
                modifier = Modifier
                    .constrainAs(pieChart){
                        top.linkTo(barChart.bottom)
                    }
            )
        }
    }
}