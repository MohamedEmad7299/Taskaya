package com.ug.taskaya.ui.ranks_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.data.fake_data.FakeData

@Composable
fun RanksScreen(navController: NavController){

    RanksContent(
        onClickBackButton = { navController.popBackStack() }
    )
}

@Composable
fun RanksContent(
    onClickBackButton: () -> Unit
){

    ConstraintLayout(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        val (backArrow, ranksText, ranks) = createRefs()

        IconButton(
            modifier = Modifier.constrainAs(backArrow) {
                top.linkTo(parent.top, 12.dp)
                start.linkTo(parent.start)
            },
            onClick = onClickBackButton
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = ""
            )
        }

        Text(
            modifier = Modifier.constrainAs(ranksText) {
                top.linkTo(backArrow.top)
                bottom.linkTo(backArrow.bottom)
                start.linkTo(backArrow.end, 16.dp)
            },
            text = "Ranks",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF090909),
                textAlign = TextAlign.Center,
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
                .constrainAs(ranks) {
                    top.linkTo(ranksText.bottom, 32.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            items(FakeData.ranks){ rank ->

                RankItem(
                    rank = rank.second,
                    rankColor = rank.third,
                    requiredTasksNumber = rank.first.first
                )
            }
        }
    }
}

@Composable
fun RankItem(
    modifier: Modifier = Modifier,
    rank: String,
    requiredTasksNumber: Int,
    rankColor: Color,
){

    Column(
        modifier = modifier.padding(start = 24.dp , end = 24.dp , top = 8.dp , bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = rank,
            style = TextStyle(
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = rankColor,
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = if (requiredTasksNumber == 0) "Complete over 10 tasks to get your first rank" else "Complete  over $requiredTasksNumber tasks",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF777777),
            )
        )
    }
}