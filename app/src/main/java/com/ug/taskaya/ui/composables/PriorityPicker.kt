package com.ug.taskaya.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ug.taskaya.ui.theme.OffWhite

@Composable
fun PriorityPicker(
    modifier: Modifier = Modifier
){

    var clicked by remember { mutableStateOf(false) }
    var currentColor by remember { mutableStateOf(Color(0xFFD9D9D9)) }

    if (!clicked)

        IconButton(
            modifier = modifier
                .padding(top = 16.dp, end = 8.dp),
            onClick = {
                clicked = true
            }
        ) {
            Canvas(modifier = Modifier
                .size(24.dp)) {
                drawCircle(
                    color = currentColor,
                    radius = size.minDimension / 2,
                    center = center
                )
            }
        }

    else

        Row(
            modifier = modifier.padding(top = 28.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Canvas(modifier = Modifier
                .clickable {
                    clicked = false
                    currentColor = Color.Red
                }
                .padding(end = 16.dp)
                .size(24.dp)) {
                drawCircle(
                    color = Color.Red,
                    radius = size.minDimension / 2,
                    center = center
                )
            }

            Canvas(modifier = Modifier
                .clickable {
                    clicked = false
                    currentColor = Color.Yellow
                }
                .padding(end = 16.dp)
                .size(24.dp)) {
                drawCircle(
                    color = Color.Yellow,
                    radius = size.minDimension / 2,
                    center = center
                )
            }

            Canvas(modifier = Modifier
                .clickable {
                    clicked = false
                    currentColor = OffWhite
                }
                .padding(end = 16.dp)
                .size(24.dp)) {
                drawCircle(
                    color = OffWhite,
                    radius = size.minDimension / 2,
                    center = center
                )
            }
        }
}