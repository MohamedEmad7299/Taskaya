package com.ug.taskaya.ui.profile_screen

import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.CircularImage
import com.ug.taskaya.ui.composables.TasksBox

@Composable
fun ProfileScreen(navController: NavController){

    ProfileContent()
}


@Preview
@Composable
fun ProfileContent(){


    val scrollState = rememberScrollState()

    val weekDays = listOf("SAT","SUN","MON","TUE","WED","THU","FRI")

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(Color.White)
            .fillMaxSize()
    ){
        
        ConstraintLayout{


            val (avatar,name,title,overview,
                tasks,chart,weekTasks) = createRefs()

            CircularImage(
                modifier = Modifier.constrainAs(avatar){
                    top.linkTo(parent.top,16.dp)
                    start.linkTo(parent.start,16.dp)
                },
                imageId = R.drawable.k
            )

            Text(
                modifier = Modifier.constrainAs(name){
                    top.linkTo(parent.top,24.dp)
                    start.linkTo(avatar.end,16.dp)
                },
                text = "Mohamed Emad",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF090909),
                    textAlign = TextAlign.Center,
                )
            )

            Text(
                modifier = Modifier.constrainAs(title){
                    top.linkTo(name.bottom,8.dp)
                    start.linkTo(avatar.end,16.dp)
                },
                text = "Android Developer",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF777777),
                )
            )

            Text(
                modifier = Modifier.constrainAs(overview){
                    top.linkTo(avatar.bottom,32.dp)
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
                    .constrainAs(tasks) {
                        top.linkTo(overview.bottom, 16.dp)
                    }
            ){
                TasksBox(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f)
                )

                TasksBox(
                    label = "Pending Tasks",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                )
            }

            ConstraintLayout(
                modifier = Modifier
                    .constrainAs(chart) {
                        top.linkTo(tasks.bottom, 16.dp)
                    }
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFD1E3E2), RoundedCornerShape(5.dp))
            ){

                val (chartTitle,arrow,date,lowLine,
                    days,upperLine,digits,noTasksText) = createRefs()

                Text(
                    modifier = Modifier
                        .constrainAs(chartTitle){
                            top.linkTo(parent.top,16.dp)
                            start.linkTo(parent.start,16.dp)
                        },
                    text = "Completion of Daily Tasks",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF777777),
                        textAlign = TextAlign.Center,
                    )
                )


                Icon(
                    modifier = Modifier
                        .constrainAs(arrow){
                            top.linkTo(parent.top,12.dp)
                            end.linkTo(date.start,2.dp)
                        },
                    tint = Color(0xFF777777),
                    painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                    contentDescription = "")


                Text(
                    modifier = Modifier
                        .constrainAs(date){
                            top.linkTo(parent.top,16.dp)
                            end.linkTo(parent.end,16.dp)
                        },
                    text = "16/8/2024",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF777777),
                        textAlign = TextAlign.Center,
                    )
                )

                Divider(
                    modifier = Modifier
                        .constrainAs(lowLine) {
                            bottom.linkTo(parent.bottom, 44.dp)
                        }
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    thickness = 0.5.dp
                )


                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(days) {
                            top.linkTo(lowLine.bottom, 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ){
                    for (i in 0..6){

                        Text(
                            modifier = Modifier.weight(1f),
                            text = weekDays[i],
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF777777),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }

                VerticalDivider(
                    modifier = Modifier
                        .padding(vertical = 52.dp)
                        .constrainAs(upperLine) {
                            start.linkTo(parent.start, 24.dp)
                            bottom.linkTo(parent.bottom)
                            top.linkTo(title.top)
                        }
                        .fillMaxHeight(),
                    color = Color.Black,
                    thickness = 0.5.dp
                )


                Column(
                    modifier = Modifier
                        .padding(vertical = 52.dp)
                        .constrainAs(digits) {
                            end.linkTo(upperLine.end, 8.dp)
                            bottom.linkTo(parent.bottom)
                            top.linkTo(title.top)
                        }
                        .fillMaxHeight()
                ){
                    for (i in 8 downTo 0 step 2){

                        Text(
                            modifier = Modifier.weight(1f),
                            text = i.toString(),
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF777777),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .constrainAs(noTasksText) {
                            bottom.linkTo(parent.bottom)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        },
                    text = "No Data",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA5A5A5),
                        textAlign = TextAlign.Right,
                    )
                )
            }

            Column(
                Modifier
                    .constrainAs(weekTasks){
                        top.linkTo(chart.bottom,16.dp)
                    }
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {  }
                    .background(color = Color(0xFFD1E3E2), shape = RoundedCornerShape(size = 5.dp)),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Tasks in Last 7 Days",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF777777),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }


}