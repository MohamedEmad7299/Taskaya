package com.ug.taskaya.ui.tasks_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ug.taskaya.R
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.ui.composables.LabelsBar
import com.ug.taskaya.ui.composables.TaskItem
import com.ug.taskaya.ui.theme.DarkGray
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.Screen
import com.ug.taskaya.utils.SharedState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
){

    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    if (screenState.message.isNotEmpty()){
        LaunchedEffect(key1 = screenState.launchedEffectKey){
            Toast.makeText(context, screenState.message, Toast.LENGTH_SHORT).show()
        }
    }

    TasksContent(
        screenState = screenState,
        onClickAddButton = {
            viewModel.prepareForNewTask()
            navController.navigate(Screen.WritingTaskScreen.route)
        },
        onCheckTask = viewModel::updateTask,
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
fun TasksContent(
    screenState: TasksState,
    onClickAddButton: () -> Unit,
    onCheckTask: (TaskEntity) -> Unit,
    onClickTask: (TaskEntity) -> Unit,
    onClickDelete: (TaskEntity) -> Unit,
    onClickDate: (TaskEntity) -> Unit,
    onClickStar: (TaskEntity) -> Unit,
    updateTaskOnHold: (TaskEntity) -> Unit
){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.task_animation))
    val selectedLabel by SharedState.currentLabel.collectAsState()
    val labels by SharedState.labels.collectAsState()
    val tasks by SharedState.tasks.collectAsState()
    var expandedPending by remember{ mutableStateOf(true) }
    var expandedComplete by remember{ mutableStateOf(true) }
    val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val currentLabel by SharedState.currentLabel.collectAsState()
    val context = LocalContext.current

    val showDatePicker = remember { mutableStateOf(false) }

    val noTasks = tasks.all { it.isCompleted && it.dueDate != todayDate } || tasks.isEmpty()

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

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){

        val(floatingButton, animation) = createRefs()

        if (!noTasks){

            LazyColumn(
                modifier = Modifier
                    .padding(top = 104.dp)
                    .fillMaxSize()
            ) {

                item {
                    ExpandedMenu(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { expandedPending = !expandedPending }
                            .padding(16.dp),
                        text = "Pending Tasks",
                        expanded = expandedPending
                    )
                }

                if (expandedPending) {
                    items(
                        tasks.reversed().filter { !it.isCompleted && (currentLabel == "All" || it.labels.contains(currentLabel)) },
                        key = { it.id }
                    ) { task ->
                        TaskItem(
                            task = task,
                            modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                            onClickDelete = { onClickDelete(task) },
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


                item {
                    ExpandedMenu(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { expandedComplete = !expandedComplete }
                            .padding(16.dp),
                        text = "Completed Tasks",
                        expanded = expandedComplete
                    )
                }

                if (expandedComplete) {
                    items(
                        tasks.reversed().filter { it.isCompleted &&
                                (currentLabel == "All" || it.labels.contains(currentLabel)) &&
                                it.completionDate == todayDate },
                        key = { it.id }
                    ) { task ->
                        TaskItem(
                            task = task,
                            modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                            onClickDelete = { onClickDelete(task) },
                            onClickDate = {
                                updateTaskOnHold(task)
                                showDatePicker.value = true
                            },
                            onClickTask = { onClickTask(task) },
                            onClickStar = { onClickStar(task.copy(isStared = !task.isStared)) },
                            onCheckTask = {
                                onCheckTask(task.copy(isCompleted = !task.isCompleted))
                            }
                        )
                    }
                }
            }
        }

        LabelsBar(
            labels = labels,
            onClickLabel = {},
            currentSelectedLabel = selectedLabel
        )

        if (noTasks)

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




@Composable
fun ExpandedMenu(
    modifier: Modifier = Modifier,
    text: String,
    expanded: Boolean){

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
            )
        )

        Icon(
            modifier = Modifier
                .padding(start =  16.dp)
                .size(40.dp),
            painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(id = R.drawable.arrow__down),
            tint = DarkGray,
            contentDescription = null
        )
    }
}