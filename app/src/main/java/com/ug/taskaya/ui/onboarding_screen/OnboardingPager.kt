package com.ug.taskaya.ui.onboarding_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ug.taskaya.R
import com.ug.taskaya.utils.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingPager(
    navController: NavController
){

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val images = listOf(
        R.drawable.onboarding_1,
        R.drawable.onboarding_2,
        R.drawable.onboarding_3
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        HorizontalPager(
            count = images.size,
            state = pagerState
        ) { pageIndex ->

            OnboardingScreen(
                image = painterResource(id = images[pageIndex]),
                onSkip = {
                    navController.navigate(Screen.TasksScreen.route){
                        popUpTo(navController.graph.id){
                            inclusive = false
                        }
                    }
                },
                onNext = {

                    if (pageIndex == images.size - 1) navController.navigate(Screen.TasksScreen.route){
                        popUpTo(navController.graph.id){
                            inclusive = false
                        }
                    }
                    else coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            pageIndex + 1,
                            animationSpec = tween(durationMillis = 400)
                        )
                    }
                },
                isLastPage = pageIndex == 2
            )
        }
    }
}

