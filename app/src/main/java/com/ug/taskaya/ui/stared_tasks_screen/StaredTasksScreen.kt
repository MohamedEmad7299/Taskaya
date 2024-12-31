package com.ug.taskaya.ui.stared_tasks_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.ui.composables.TaskItem

@Composable
fun StaredTasksScreen(
    navController: NavController,
    viewModel: StaredTasksViewModel = hiltViewModel()
){

    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    if (screenState.message.isNotEmpty()){
        LaunchedEffect(key1 = screenState.launchedEffectKey){
            Toast.makeText(context, screenState.message, Toast.LENGTH_SHORT).show()
        }
    }

    StaredTasksContent(
        screenState = screenState,
        onCheckTask = {
            // not yet
        },
        onClickBackIcon = { navController.popBackStack() },
        onClickTask = {},
        onClickDelete = viewModel::deleteTask,
        onClickStar = viewModel::updateTask,
        onClickDate = {

        }
    )
}

@Composable
fun StaredTasksContent(
    screenState: StaredTasksState,
    onClickBackIcon: () -> Unit,
    onCheckTask: (TaskEntity) -> Unit,
    onClickTask: (TaskEntity) -> Unit,
    onClickDelete: (Long) -> Unit,
    onClickDate: (TaskEntity) -> Unit,
    onClickStar: (TaskEntity) -> Unit,
){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
    ) {
        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val (backArrow, staredTasksText, tasks) = createRefs()

            IconButton(
                modifier = Modifier.constrainAs(backArrow) {
                    top.linkTo(parent.top, 12.dp)
                    start.linkTo(parent.start)
                },
                onClick = onClickBackIcon) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = ""
                )
            }

            Text(
                modifier = Modifier.constrainAs(staredTasksText) {
                    top.linkTo(backArrow.top)
                    bottom.linkTo(backArrow.bottom)
                    start.linkTo(backArrow.end, 16.dp)
                },
                text = "Stared Tasks",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF090909),
                    textAlign = TextAlign.Center,
                )
            )

            Column(
                modifier = Modifier.constrainAs(tasks) {
                    top.linkTo(staredTasksText.bottom,32.dp)
                },
            ){
                screenState.staredTasks.forEach { task ->

                    TaskItem(
                        task = task,
                        onClickDelete = { onClickDelete(task.id) },
                        onClickDate = { onClickDate(task) },
                        onClickTask = { onClickTask(task) },
                        onClickStar = { onClickStar(task.copy(isStared = !it.isStared)) },
                        onCheckTask = { onCheckTask(task) }
                    )
                }
            }
        }
    }
}