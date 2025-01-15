package com.ug.taskaya.utils


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ug.taskaya.R
import com.ug.taskaya.data.fake_data.FakeData
import com.ug.taskaya.ui.theme.LightGreen
import com.ug.taskaya.ui.theme.Ment
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties

@Composable
fun BarChart(
    modifier: Modifier = Modifier
){

    val tasks by SharedState.tasks.collectAsState()

    val dates = FakeData.getDatesOfThisWeek()

    val completionOfTasks: List<Double> = dates.map { date ->
        tasks.count { it.completionDate == date }.toDouble()
    }

    val barsData = remember {
        listOf(
            "SAT", "SUN", "MON", "TUE", "WED", "THU", "FRI"
        ).mapIndexed { index, label ->
            Bars(
                label = label,
                values = listOf(
                    Bars.Data(
                        label = "Completion of Daily Tasks",
                        value = completionOfTasks.getOrElse(index) { 0.0 },
                        color = SolidColor(Ment)
                    )
                )
            )
        }
    }

    Box(
        modifier = modifier
            .padding(16.dp)
            .background(LightGreen , RoundedCornerShape(size = 5.dp))
    ){
        ColumnChart(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(248.dp),
            maxValue = 100.0,
            minValue = 0.0,
            labelHelperProperties = LabelHelperProperties(
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF777777)
                )
            ),
            gridProperties = GridProperties(
                enabled = false
            ),
            labelProperties = LabelProperties(
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF777777)
                ),
                enabled = true,
                rotation = LabelProperties.Rotation(
                    degree = 0.0F
                ),
            ),
            data = barsData,
            barProperties = BarProperties(
                cornerRadius = Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
                spacing = 3.dp,
                thickness = 20.dp
            ),
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            indicatorProperties = HorizontalIndicatorProperties(
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF777777)
                )
            ),
        )
    }
}



