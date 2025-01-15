package com.ug.taskaya.ui.stared_tasks_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.ug.taskaya.utils.Screen
import com.ug.taskaya.utils.SharedState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        onCheckTask = viewModel::updateTask,
        onClickBackIcon = { navController.popBackStack() },
        onClickTask = { task ->

            SharedState.updateOnEditTask(task)
            navController.navigate(Screen.WritingTaskScreen.route)
        },
        onClickDelete = viewModel::deleteTask,
        onClickStar = viewModel::updateTask,
        onClickDate = viewModel::updateTask,
        updateTaskOnHold = viewModel::updateTaskOnHold
    )
}

@Composable
fun StaredTasksContent(
    screenState: StaredTasksState,
    onClickBackIcon: () -> Unit,
    onCheckTask: (TaskEntity) -> Unit,
    onClickTask: (TaskEntity) -> Unit,
    onClickDelete: (TaskEntity) -> Unit,
    onClickDate: (TaskEntity) -> Unit,
    onClickStar: (TaskEntity) -> Unit,
    updateTaskOnHold: (TaskEntity) -> Unit
){

    val context = LocalContext.current

    val showDatePicker = remember { mutableStateOf(false) }

    val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    if (showDatePicker.value) {

        val datePicker = android.app.DatePickerDialog(
            context,
            { _, year, month, day ->

                val selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year)
                onClickDate(screenState.taskOnHold.copy(dueDate = selectedDate))
                showDatePicker.value = false
            },
            screenState.taskOnHold.dueDate.split("/").getOrNull(2)?.toIntOrNull() ?: 2025,
            screenState.taskOnHold.dueDate.split("/").getOrNull(1)?.toIntOrNull()?.minus(1) ?: 0,
            screenState.taskOnHold.dueDate.split("/").getOrNull(0)?.toIntOrNull() ?: 1
        )

        datePicker.datePicker.minDate = java.util.Calendar.getInstance().timeInMillis

        datePicker.setOnCancelListener {
            showDatePicker.value = false
        }

        datePicker.setOnDismissListener {
            showDatePicker.value = false
        }

        datePicker.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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

            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .constrainAs(tasks) {
                        top.linkTo(staredTasksText.bottom, 32.dp)
                    },
            ) {
                items(
                    screenState.staredTasks,
                    key = { it.id }
                ) { task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                        onClickDelete = {
                            onClickDelete(task)
                        },
                        onClickDate = {
                            updateTaskOnHold(task)
                            showDatePicker.value = true
                        },
                        onClickTask = { onClickTask(task) },
                        onClickStar = { onClickStar(task.copy(isStared = !task.isStared)) },
                        onCheckTask = {
                            onCheckTask(task.copy(
                                isCompleted = !task.isCompleted,
                                completionDate = todayDate
                            ))
                        }
                    )
                }
            }
        }
    }
}