package com.ug.taskaya.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun OutlinedButtonTaskaya(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconId: Int? = null,
    label: String
){


    OutlinedButton(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color.White
            ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row {

            if (iconId != null){

                Image(
                    modifier = Modifier.padding(end = 16.dp),
                    painter = painterResource(id = iconId),
                    contentDescription = "") }

            Text(
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}