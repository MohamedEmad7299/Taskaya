package com.ug.taskaya.ui.settings_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.AboutItem
import com.ug.taskaya.ui.composables.DateItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
){

    val context = LocalContext.current

    val screenState by viewModel.screenState.collectAsState()

    if (screenState.message.isNotEmpty()){
        LaunchedEffect(key1 = screenState.launchedEffectKey){
            Toast.makeText(context, screenState.message, Toast.LENGTH_SHORT).show()
        }
    }

    SettingsContent(
        fakeAction = { Toast.makeText(context, "بس يا بابا خف لعب", Toast.LENGTH_SHORT).show() },
        onLogOut = {

            viewModel.signOut(navController)
        },
        onClickBackIcon = { navController.popBackStack() }
    )
}


@Composable
fun SettingsContent(
    fakeAction: () -> Unit,
    onLogOut: () -> Unit,
    onClickBackIcon: () -> Unit
){

    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        val (settings,backArrow,date,firstDay,timeFormat,
            dateFormat,line,about,language,rate,privacy, logOut) = createRefs()


        IconButton(
            modifier = Modifier.constrainAs(backArrow){
                top.linkTo(parent.top,12.dp)
                start.linkTo(parent.start) },
            onClick = onClickBackIcon) {
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
            value = "System default",
            onClick = fakeAction)

        DateItem(
            modifier = Modifier.constrainAs(timeFormat){
                top.linkTo(firstDay.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.time_icon,
            label = "Time Format",
            value = "System default",
            onClick = fakeAction)

        DateItem(
            modifier = Modifier.constrainAs(dateFormat){
                top.linkTo(timeFormat.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.format_icon,
            label = "Date Format",
            value = currentDate.format(formatter),
            onClick = fakeAction)

        HorizontalDivider(modifier = Modifier
            .constrainAs(line) {
                top.linkTo(dateFormat.bottom, 16.dp)
            }
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color.Black)

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
            label = "Language",
            onClick = fakeAction)

        AboutItem(
            modifier = Modifier.constrainAs(rate){
                top.linkTo(language.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.rate_icon,
            label = "Rate Us",
            onClick = fakeAction)

        AboutItem(
            modifier = Modifier.constrainAs(privacy){
                top.linkTo(rate.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.privacy_icon,
            label = "Privacy Policy",
            onClick = fakeAction)

        AboutItem(
            modifier = Modifier.constrainAs(logOut){
                top.linkTo(privacy.bottom,24.dp)
                start.linkTo(parent.start,16.dp)
            },
            iconId = R.drawable.out,
            label = "Log Out",
            onClick = onLogOut)
    }
}