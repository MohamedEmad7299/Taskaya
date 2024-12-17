package com.ug.taskaya.ui.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.AboutItem
import com.ug.taskaya.ui.composables.DateItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Preview
@Composable
fun SettingsScreen(){

    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        val (settings,backArrow,date,firstDay,timeFormat,
            dateFormat,line,about,language,rate,privacy) = createRefs()


        IconButton(
            modifier = Modifier.constrainAs(backArrow){
            top.linkTo(parent.top,12.dp)
            start.linkTo(parent.start) },
            onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = ""
            )
        }

        Text(
            modifier = Modifier.constrainAs(settings){
                top.linkTo(parent.top,24.dp)
                start.linkTo(backArrow.end)
            },
            text = "Settings",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight(600)
        )

        Text(
            modifier = Modifier.constrainAs(date){
                top.linkTo(settings.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            text = "Date & Time",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF777777),
            )
        )

        DateItem(
            modifier = Modifier.constrainAs(firstDay){
                top.linkTo(date.bottom,16.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.calendar_iconn,
            label = "First Day of Week",
            value = "System default") {}

        DateItem(
            modifier = Modifier.constrainAs(timeFormat){
                top.linkTo(firstDay.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.time_icon,
            label = "Time Format",
            value = "System default") {}


        DateItem(
            modifier = Modifier.constrainAs(dateFormat){
                top.linkTo(timeFormat.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.format_icon,
            label = "Date Format",
            value = currentDate.format(formatter)
        ) {}

        Divider(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(dateFormat.bottom, 16.dp)
                }
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            color = Color.Black,
            thickness = 0.5.dp
        )

        Text(
            modifier = Modifier.constrainAs(about){
                top.linkTo(line.bottom,16.dp)
                start.linkTo(parent.start,16.dp)
            },
            text = "About",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF777777),
            )
        )

        AboutItem(
            modifier = Modifier.constrainAs(language){
                top.linkTo(about.bottom,16.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.language,
            label = "Language"){}

        AboutItem(
            modifier = Modifier.constrainAs(rate){
                top.linkTo(language.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.rate_icon,
            label = "Rate Us"){}

        AboutItem(
            modifier = Modifier.constrainAs(privacy){
                top.linkTo(rate.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.privacy_icon,
            label = "Privacy Policy"){}
    }
}