package com.ug.taskaya.ui.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ug.taskaya.R
import com.ug.taskaya.data.fake_data.FakeData
import com.ug.taskaya.ui.theme.LightGreen
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.SharedState
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun PieeChart(
    modifier: Modifier = Modifier
){

    val tasks by SharedState.tasks.collectAsState()
    val currentWeekDays = FakeData.getDatesOfThisWeek()

    val pendingCount = tasks.filter { !it.isCompleted}.size
    val completedCount = tasks.filter { it.isCompleted && it.completionDate in currentWeekDays}.size


    var data by remember {mutableStateOf(listOf(
        Pie(label = "Pending Tasks", data = pendingCount.toDouble(), color = Color(0xFF82B0AE), selectedColor = Color(0xFF82B0AE)),
        Pie(label = "Completed Tasks", data = completedCount.toDouble(), color = Ment, selectedColor = Ment),
    ))}


    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(200.dp)
            .fillMaxWidth()
            .background(LightGreen , RoundedCornerShape(size = 5.dp))
    ){
        val (titleText,pieChart, week, counters) = createRefs()

        Text(
            modifier = Modifier.constrainAs(titleText){
                start.linkTo(parent.start , 16.dp)
                top.linkTo(parent.top , 16.dp)
            },
            text = "Pending vs. Completed Tasks",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF777777),
            )
        )

        Text(
            modifier = Modifier.constrainAs(week){
                start.linkTo(parent.start , 16.dp)
                top.linkTo(titleText.bottom , 8.dp)
            },
            text = "This Week Progress",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF777777),
                fontStyle = FontStyle.Italic
            )
        )

        PendingVsCompleted(
            pendingNumber = pendingCount,
            completedNumber = completedCount,
            modifier = Modifier.constrainAs(counters){
                top.linkTo(week.bottom)
                start.linkTo(parent.start, 24.dp)
                bottom.linkTo(parent.bottom)
            }
        )

        PieChart(
            modifier = Modifier
                .constrainAs(pieChart){
                    end.linkTo(parent.end , 16.dp)
                    top.linkTo(titleText.bottom, 16.dp)
                }
                .size(100.dp),
            data = data,
            onPieClick = {
                val pieIndex = data.indexOf(it)
                data = data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
            },
            selectedScale = 1.2f,
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
            spaceDegree = 7f,
            selectedPaddingDegree = 4f,
            style = Pie.Style.Stroke(width = 25f.dp)
        )
    }
}


@Composable
fun PendingVsCompleted(
    modifier: Modifier = Modifier,
    pendingNumber: Int,
    completedNumber: Int,
){

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){
        Row(
            Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Box(
                Modifier
                    .padding(end = 8.dp)
                    .size(20.dp)
                    .background(color = Ment, shape = RoundedCornerShape(size = 2.dp))
            ){

            }

            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = "Completed",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )

            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = completedNumber.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Ment,
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){

            Box(
                Modifier
                    .padding(end = 8.dp)
                    .size(20.dp)
                    .background(color = Color(0xFF82B0AE), shape = RoundedCornerShape(size = 2.dp))
            ){

            }

            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = "Pending",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )

            Text(
                modifier = Modifier
                    .padding(end = 8.dp),
                text = pendingNumber.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Ment,
                )
            )
        }
    }
}