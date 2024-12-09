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
}
