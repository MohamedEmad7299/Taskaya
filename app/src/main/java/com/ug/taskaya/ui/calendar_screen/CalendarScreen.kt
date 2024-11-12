package com.ug.taskaya.ui.calendar_screen

import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.FakeData


@Composable
fun CalenderScreen(navController: NavController){

    CalendarContent()
}


@Preview
@Composable
fun CalendarContent(){

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ){
        val (tryText,calendar,todayText,quote) = createRefs()

        Text(
            modifier = Modifier.constrainAs(tryText){
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(parent.top,32.dp)
            },
            text = "Try Something Different Today",
            fontSize = 16.sp,
            color = Ment,
            fontFamily = FontFamily(Font(R.font.inter))
        )


        AndroidView(
            factory = { CalendarView(it) },
            modifier = Modifier
                .constrainAs(calendar) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(tryText.bottom, 24.dp)
                }
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Text(
            modifier = Modifier.constrainAs(todayText){
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(calendar.bottom)
            },
            text = "Today Quote",
            fontSize = 18.sp,
            color = Ment,
            fontWeight = FontWeight(1000),
            fontFamily = FontFamily(Font(R.font.inter))
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .constrainAs(quote) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(todayText.bottom, 16.dp)
                },
            text = "“${FakeData.timeManagementQuotes.random()}”",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            textAlign = TextAlign.Center
        )
    }
}
