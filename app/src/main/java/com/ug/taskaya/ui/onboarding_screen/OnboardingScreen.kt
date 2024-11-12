package com.ug.taskaya.ui.onboarding_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ug.taskaya.R

@Composable
fun OnboardingScreen(
    image: Painter,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    isLastPage: Boolean = false
){

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ){

        val (skipButton,nextButton) = createRefs()

        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        TextButton(
            modifier = Modifier.constrainAs(skipButton){
                top.linkTo(parent.top,32.dp)
                end.linkTo(parent.end,32.dp)
            },
            onClick = { onSkip() }
        ) {
            Text("Skip",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.inter)),
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
        }


        if (isLastPage) {

            TextButton(
                modifier = Modifier.constrainAs(nextButton){
                    bottom.linkTo(parent.bottom,64.dp)
                    end.linkTo(parent.end,32.dp)
                },
                onClick = { onSkip() }
            ) {
                Text("Done",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }

        } else {

            IconButton(
                modifier = Modifier.constrainAs(nextButton){
                    bottom.linkTo(parent.bottom,64.dp)
                    end.linkTo(parent.end,32.dp)
                },
                onClick = { onNext() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        }
    }
}
