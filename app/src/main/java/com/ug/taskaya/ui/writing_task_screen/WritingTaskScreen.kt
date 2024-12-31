package com.ug.taskaya.ui.writing_task_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.ug.taskaya.ui.composables.CustomChips
import com.ug.taskaya.ui.composables.DetailsItem
import com.ug.taskaya.ui.composables.PriorityPicker
import com.ug.taskaya.ui.theme.DarkGray
import com.ug.taskaya.ui.theme.Gold
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.Screen


@Composable
fun WritingTaskScreen(
    navController: NavController,
    viewModel: WritingTaskViewModel = hiltViewModel()
){

    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){

        viewModel.collectSelectedLabels()
    }

    if (screenState.message.isNotEmpty()){
        LaunchedEffect(key1 = screenState.launchedEffectKey){
            Toast.makeText(context, screenState.message, Toast.LENGTH_SHORT).show()
        }
    }

    WritingTaskContent(
        screenState = screenState,
        saveTask = { viewModel.saveTask(navController) },
        newTask = screenState.task,
        onClickLabels = { navController.navigate(Screen.LabelsScreen.route) },
        onChangeTaskContent = viewModel::onChangeTaskContent,
        onClickBackArrow = { navController.popBackStack() },
        onClickRepeatedButton = viewModel::onChangeRepetitionState,
        onClickStarButton = viewModel::onChangeStaredState,
        onClickDate = viewModel::onChangeTaskDueDate,
        updatePriority = viewModel::updatePriority
    )
}


@Composable
fun WritingTaskContent(
    screenState: WritingTaskState,
    newTask: TaskEntity = screenState.task,
    saveTask: () -> Unit,
    onClickBackArrow: () -> Unit,
    onChangeTaskContent: (String) -> Unit,
    onClickLabels: () -> Unit,
    onClickRepeatedButton: () -> Unit,
    onClickDate: (String) -> Unit,
    onClickStarButton: () -> Unit,
    updatePriority: (Color) -> Unit
){

    val scrollState = rememberScrollState()

    val context = LocalContext.current

    val showDatePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        val datePicker = android.app.DatePickerDialog(
            context,
            { _, year, month, day ->

                val selectedDate = "$day/${month + 1}/$year"
                onClickDate(selectedDate)
                showDatePicker.value = false
            },
            newTask.dueDate.split("/").getOrNull(2)?.toIntOrNull() ?: 2024,
            newTask.dueDate.split("/").getOrNull(1)?.toIntOrNull()?.minus(1) ?: 0,
            newTask.dueDate.split("/").getOrNull(0)?.toIntOrNull() ?: 1
        )

        datePicker.datePicker.minDate = java.util.Calendar.getInstance().timeInMillis

        datePicker.show()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
    ){
        ConstraintLayout(
            Modifier.fillMaxSize()
        ){

            val (backArrow,category,task, divider1,
                divider2,divider3,divider4,dueDate,
                date,repeatTask,noOrYes,priority,color,
                addImage, starButton, saveButton) = createRefs()


            if (screenState.savingState == SavingState.Loading)

                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(saveButton){
                            end.linkTo(parent.end,16.dp)
                            top.linkTo(parent.top,24.dp)
                        }
                        .size(32.dp),
                    color = Ment,
                    strokeWidth = 5.dp
                )

            else

                Text(
                    modifier = Modifier
                        .constrainAs(saveButton){
                            end.linkTo(parent.end,16.dp)
                            top.linkTo(parent.top,24.dp)
                        }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ){ saveTask() },
                    text = "Save",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Ment,
                        textAlign = TextAlign.Center,
                    )
                )

            IconButton(
                modifier = Modifier.constrainAs(backArrow){
                    top.linkTo(parent.top,16.dp)
                    start.linkTo(parent.start) },
                onClick = onClickBackArrow) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = ""
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .padding(horizontal = 16.dp)
                    .constrainAs(task){
                        top.linkTo(backArrow.bottom)
                    },
                value = newTask.taskContent,
                onValueChange = {
                    onChangeTaskContent(it)
                },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black ,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                ),
                placeholder = { Text(
                    text = "What would you like to do ?",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF777777),
                    )
                ) }
            )

            CustomChips(
                modifier = Modifier
                    .clickable {
                        onClickLabels()
                    }
                    .constrainAs(category){
                        top.linkTo(task.bottom,16.dp)
                    },
                labels = newTask.labels.ifEmpty { listOf("Label") })


            HorizontalDivider(modifier = Modifier
                .constrainAs(divider1) {
                    top.linkTo(category.bottom, 16.dp)
                }
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.Black)

            DetailsItem(
                modifier = Modifier.constrainAs(dueDate){
                    top.linkTo(divider1.bottom,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                iconId = R.drawable.solar_calendar,
                text = "Due Date")

            Column(
                Modifier
                    .constrainAs(date) {
                        top.linkTo(divider1.bottom, 24.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
                    .clickable {
                        showDatePicker.value = true
                    }
                    .wrapContentWidth()
                    .height(30.dp)
                    .background(color = Color(0xFFD1E3E2), shape = RoundedCornerShape(size = 5.dp))
                    .padding(start = 9.dp, top = 8.dp, end = 9.dp, bottom = 7.dp)
            ){
                Text(
                    text = newTask.dueDate,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF777777),
                        textAlign = TextAlign.Center,
                    )
                )
            }

            HorizontalDivider(modifier = Modifier
                .constrainAs(divider2) {
                    top.linkTo(dueDate.bottom, 24.dp)
                }
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.Black)

            DetailsItem(
                modifier = Modifier.constrainAs(repeatTask){
                    top.linkTo(divider2.bottom,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                iconId = R.drawable.repeat,
                text = "Repeat Task")

            HorizontalDivider(modifier = Modifier
                .constrainAs(divider3) {
                    top.linkTo(repeatTask.bottom, 24.dp)
                }
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.Black)

            DetailsItem(
                modifier = Modifier.constrainAs(priority){
                    top.linkTo(divider3.bottom,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                iconId = R.drawable.flag,
                text = "Priority")


            HorizontalDivider(modifier = Modifier
                .constrainAs(divider4) {
                    top.linkTo(priority.bottom, 24.dp)
                }
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.Black)

            DetailsItem(
                modifier = Modifier.constrainAs(addImage){
                    top.linkTo(divider4.bottom,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                iconId = R.drawable.stars,
                text = "Is Stared")

            Button(
                onClick = onClickRepeatedButton,
                modifier = Modifier
                    .constrainAs(noOrYes) {
                        top.linkTo(divider2.bottom, 24.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
                    .width(60.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD1E3E2),
                    contentColor = Color(0xFF777777)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = if (newTask.isRepeated) "Yes" else "No",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        textAlign = TextAlign.Center
                    )
                )
            }


            PriorityPicker(
                modifier = Modifier
                    .constrainAs(color) {
                        top.linkTo(divider3.bottom)
                        end.linkTo(parent.end)
                    },
                updatePriority = updatePriority
            )

            IconButton(
                modifier = Modifier
                    .constrainAs(starButton) {
                        top.linkTo(divider4.bottom, 8.dp)
                        end.linkTo(parent.end, 8.dp)
                    },
                onClick = onClickStarButton
            ){
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.star_filled),
                    contentDescription = "",
                    tint = if (newTask.isStared) Gold else DarkGray
                )
            }
        }
    }
}