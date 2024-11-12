package com.ug.taskaya.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ug.taskaya.R


@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    iconId: Int,
    label: String,
    value: String,
    onClick: () -> Unit
){


    Row(
        modifier = modifier
            .clickable { onClick() }
            .background(Color.White)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "")

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ){
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF090909),
                    textAlign = TextAlign.Center,
                )
            )

            Text(
                text = value,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF777777),
                )
            )
        }
    }

}