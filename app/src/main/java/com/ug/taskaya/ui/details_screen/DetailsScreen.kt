package com.ug.taskaya.ui.details_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ug.taskaya.R
import com.ug.taskaya.ui.composables.CustomChips
import com.ug.taskaya.ui.composables.DetailsItem
import com.ug.taskaya.ui.theme.Ment


@Composable
fun DetailsScreen(){

    val scrollState = rememberScrollState()
    var categories by remember { mutableStateOf(listOf("Work","Professional","Home","Bobo","Lolo","Sa8ery")) }



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
                addImage,addButton) = createRefs()


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
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(task){
                    top.linkTo(backArrow.bottom,32.dp)
                },
                text = "Go to gym",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF090909),
                ),
                textAlign = TextAlign.Start
            )

            CustomChips(
                modifier = Modifier
                    .clickable {  }
                    .constrainAs(category){
                        bottom.linkTo(divider1.top,16.dp)
                    },
                categories = categories)


            Divider(
                modifier = Modifier
                    .constrainAs(divider1) {
                        top.linkTo(task.bottom, 256.dp)
                    }
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                thickness = 0.5.dp
            )

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
                    .clickable { }
                    .width(90.dp)
                    .height(30.dp)
                    .background(color = Color(0xFFD1E3E2), shape = RoundedCornerShape(size = 5.dp))
                    .padding(start = 9.dp, top = 8.dp, end = 9.dp, bottom = 7.dp)
            ){
                Text(
                    text = "20/8/2024",
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

            Divider(
                modifier = Modifier
                    .constrainAs(divider2) {
                        top.linkTo(dueDate.bottom, 24.dp)
                    }
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                thickness = 0.5.dp
            )

            DetailsItem(
                modifier = Modifier.constrainAs(repeatTask){
                    top.linkTo(divider2.bottom,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                iconId = R.drawable.repeat,
                text = "Repeat Task")

            Divider(
                modifier = Modifier
                    .constrainAs(divider3) {
                        top.linkTo(repeatTask.bottom, 24.dp)
                    }
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                thickness = 0.5.dp
            )

            DetailsItem(
                modifier = Modifier.constrainAs(priority){
                    top.linkTo(divider3.bottom,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                iconId = R.drawable.flag,
                text = "Priority")


            Divider(
                modifier = Modifier
                    .constrainAs(divider4) {
                        top.linkTo(priority.bottom, 24.dp)
                    }
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                thickness = 0.5.dp
            )

            DetailsItem(
                modifier = Modifier.constrainAs(addImage){
                    top.linkTo(divider4.bottom,24.dp)
                    start.linkTo(parent.start,16.dp)
                },
                iconId = R.drawable.image,
                text = "Add Image")

            Column(
                Modifier
                    .constrainAs(noOrYes) {
                        top.linkTo(divider2.bottom, 24.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
                    .clickable { }
                    .width(60.dp)
                    .height(30.dp)
                    .background(color = Color(0xFFD1E3E2), shape = RoundedCornerShape(size = 5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "No",
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

            IconButton(
                modifier = Modifier
                    .constrainAs(color) {
                        top.linkTo(divider3.bottom, 16.dp)
                        end.linkTo(parent.end, 8.dp)
                    },
                onClick = { /*TODO*/ }
            ) {
                Canvas(modifier = Modifier
                    .size(24.dp)) {
                    drawCircle(
                        color = Color(0xFFD9D9D9),
                        radius = size.minDimension / 2,
                        center = center
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .constrainAs(addButton) {
                        top.linkTo(divider4.bottom, 16.dp)
                        end.linkTo(parent.end, 8.dp)
                    },
                onClick = { /*TODO*/ }
            ){
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "",
                    tint = Ment)
            }
        }
    }
}