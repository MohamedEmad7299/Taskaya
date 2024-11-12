package com.ug.taskaya.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ug.taskaya.R


@Preview
@Composable
fun TasksBox(
    modifier: Modifier = Modifier,
    numberOfTasks: Int = 0,
    label: String = "Completed Tasks"
){
    Column(
        modifier
            .fillMaxWidth()
            .height(109.dp)
            .background(color = Color(0xFFD1E3E2), shape = RoundedCornerShape(size = 5.dp))
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = numberOfTasks.toString(),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(500),
                color = Color(0xFF090909),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF777777),
                textAlign = TextAlign.Center,
            )
        )
    }
}