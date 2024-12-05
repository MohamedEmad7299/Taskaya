package com.ug.taskaya.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.DarkGray

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClickBack : () -> Unit
){


    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Back Icon",
            tint = DarkGray
        )

        Text(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ){
                onClickBack()
            },
            text = AnnotatedString(" Back to log in"),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = DarkGray,
                textAlign = TextAlign.Center,
            )
        )
    }
}