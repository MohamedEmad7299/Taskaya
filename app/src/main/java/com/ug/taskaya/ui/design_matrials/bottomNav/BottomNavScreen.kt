package com.ug.taskaya.ui.design_matrials.bottomNav

import com.ug.taskaya.R
import com.ug.taskaya.utils.Screen

sealed class BottomNavScreen(

    val route: String,
    val icon : Int,
    val label: String
){
    data object More : BottomNavScreen(
        route = Screen.MoreScreen.route,
        icon = R.drawable.more_icon,
        label = "More"
    )
    data object Tasks : BottomNavScreen(
        route = Screen.TasksScreen.route,
        icon = R.drawable.tasks_icon,
        label = "Tasks"
    )

    data object Calender : BottomNavScreen(
        route = Screen.CalenderScreen.route,
        icon = R.drawable.calender_icon,
        label = "Calender"
    )

    data object Profile : BottomNavScreen(
        route = Screen.ProfileScreen.route,
        icon = R.drawable.profile_icon,
        label = "Profile"
    )
}
