package com.ug.taskaya.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    imageId: Int,
    size: Int = 70
) {

    Image(
        painter = painterResource(id = imageId),
        contentDescription = "Circular Image",
        modifier = modifier
            .clip(CircleShape)
            .size(size.dp),
        contentScale = ContentScale.Crop
    )
}