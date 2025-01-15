package com.ug.taskaya.utils

sealed class Screen (val route: String){

    data object SplashScreen : Screen("splash_screen")
    data object SignInScreen : Screen("signIn_screen")
    data object SignUpScreen : Screen("signUp_screen")
    data object MoreScreen : Screen("more_screen")
    data object TasksScreen : Screen("tasks_screen")
    data object CalenderScreen : Screen("calender_screen")
    data object ProfileScreen : Screen("profile_screen")
    data object OnboardingScreen : Screen("onboarding_screen")
    data object ResetPasswordScreen : Screen("reset_password_screen")
    data object NoInternetScreen : Screen("no_internet_screen")
    data object LabelsScreen : Screen("labels_screen")
    data object WritingTaskScreen : Screen("writing_task_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object DeleteLabelsScreen : Screen("delete_labels_screen")
    data object StaredTasksScreen : Screen("stared_tasks_screen")
    data object RanksScreen : Screen("ranks_screen")
}
