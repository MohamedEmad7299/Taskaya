package com.ug.taskaya.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Icon
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
fun DetailsItem(
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Color(0xFF777777))

        Text(
            modifier = modifier.padding(start = 16.dp),
            text = text,
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