package com.ug.taskaya.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ug.taskaya.R
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.ui.theme.Gold
import com.ug.taskaya.ui.theme.Ment
import kotlinx.coroutines.launch
import kotlin.math.abs


@Composable
fun TaskItem(
    task: TaskEntity,
    onClickTask: () -> Unit,
    onClickStar: (TaskEntity) -> Unit,
    onClickDelete: (Long) -> Unit,
    onClickDate: (TaskEntity) -> Unit,
    onCheckTask: (TaskEntity) -> Unit,
){

    val swipeOffset = remember { Animatable(0f) }
    val maxSwipeOffset = 500f
    val coroutineScope = rememberCoroutineScope()
    var isCompleted by remember { mutableStateOf(task.isCompleted) }
    val cornerRadius = 5.dp

    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable { onClickTask() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = Color(0x0D2F1919)),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionButton(text = "Star", backgroundColor = Gold,
                iconId = R.drawable.star_outlined,
                onClick = {
                    onClickStar(task)
                },
                isStaredInitialValue = task.isStared)

            ActionButton(text = "Date",
                backgroundColor = Ment,
                iconId = R.drawable.solar_calendar,
                onClick = {
                    onClickDate(task)
                },
                isStaredInitialValue = task.isStared)

            ActionButton(text = "Delete",
                backgroundColor = Color.Red,
                iconId = R.drawable.delete_icon,
                onClick = {
                    onClickDelete(task.id)
                },
                isStaredInitialValue = task.isStared)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .offset { androidx.compose.ui.unit.IntOffset(swipeOffset.value.toInt(), 0) }
                .clip(RoundedCornerShape(cornerRadius))
                .background(color = Color(0xFFe0e0e0))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = swipeOffset.value + dragAmount
                            coroutineScope.launch {
                                swipeOffset.snapTo(newOffset.coerceIn(-maxSwipeOffset, 0f))
                            }
                        },
                        onDragEnd = {
                            if (abs(swipeOffset.value) > maxSwipeOffset / 2) {
                                coroutineScope.launch {
                                    swipeOffset.animateTo(-maxSwipeOffset, tween(300))
                                }
                            } else {
                                coroutineScope.launch {
                                    swipeOffset.animateTo(0f, tween(300))
                                }
                            }
                        }
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    isCompleted = !isCompleted
                    onCheckTask(task)
                }
            ){
                if (isCompleted) {
                    Image(
                        painter = painterResource(R.drawable.checked_icon),
                        contentDescription = "",
                        Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.unchecked_icon),
                        contentDescription = ""
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = task.taskContent,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = if (isCompleted) Color(0xFF777777) else Color.Black,
                ),
                textDecoration = if (isCompleted) TextDecoration.LineThrough else null
            )

            // Spacer to push the star icon to the end
            Spacer(modifier = Modifier.weight(1f))

            if (task.isStared) {
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    painter = painterResource(id = R.drawable.stars),
                    contentDescription = "",
                    tint = Ment
                )
            }
        }
    }
}


@Composable
fun ActionButton(
    text: String,
    backgroundColor: Color,
    iconId: Int,
    onClick: () -> Unit,
    isStaredInitialValue: Boolean
){

    var isStared by remember { mutableStateOf(isStaredInitialValue) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(60.dp)
            .background(backgroundColor)
            .clickable(onClick = {
                isStared = !isStared
                onClick()
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Icon(painter = painterResource(
            if (backgroundColor == Gold && isStared)
                R.drawable.star_filled
            else
                iconId),
            contentDescription = "",
            tint = Color.White)

        Text(
            text =
            if (backgroundColor == Gold && isStared)
                "un star"
                else
                    text,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        )
    }
}

