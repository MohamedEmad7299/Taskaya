package com.ug.taskaya.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ug.taskaya.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomChips(
    modifier: Modifier = Modifier,
    labels: List<String>
){

    FlowRow (
        modifier = modifier
            .padding(horizontal = 8.dp)
            .background(Color.White)
            .wrapContentHeight()
            .fillMaxWidth()
    ){
        labels.forEach { category ->

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 16.dp, end = 8.dp)
                    .wrapContentWidth()
                    .height(34.dp)
                    .background(color = Color(0xFFF5F4F4), shape = RoundedCornerShape(size = 5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier.padding(horizontal =  16.dp),
                    text = category,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF777777),
                    )
                )
            }
        }
    }
}