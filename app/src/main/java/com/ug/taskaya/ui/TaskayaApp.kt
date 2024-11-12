package com.ug.taskaya.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.ug.taskaya.ui.calendar_screen.CalenderScreen
import com.ug.taskaya.ui.design_matrials.bottomNav.BottomNavScreen
import com.ug.taskaya.ui.design_matrials.bottomNav.BottomNavigationBar
import com.ug.taskaya.ui.drawer.SideMenu
import com.ug.taskaya.ui.onboarding_screen.OnboardingPager
import com.ug.taskaya.ui.profile_screen.ProfileScreen
import com.ug.taskaya.ui.sign_in_screen.SignInScreen
import com.ug.taskaya.ui.sign_up_screen.SignUpScreen
import com.ug.taskaya.ui.splash_screen.SplashScreen
import com.ug.taskaya.ui.tasks_screen.TasksScreen
import com.ug.taskaya.utils.Screen
import com.ug.taskaya.utils.isInternetConnected

@Composable
fun TaskayaApp() {

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination
    val context = LocalContext.current

    val bottomBarScreens = listOf(

        BottomNavScreen.More.route,
        BottomNavScreen.Tasks.route,
        BottomNavScreen.Calender.route,
        BottomNavScreen.Profile.route
    )


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            SideMenu()
        }
    ) {

        Scaffold(

            bottomBar = {
                if (currentDestination?.route in bottomBarScreens) {
                    BottomNavigationBar(
                        currentScreen = currentDestination!!,
                        onNavigate = { screen ->
                            if (isInternetConnected(context)) {
                                if (screen.route == BottomNavScreen.More.route) {
                                    // Open the drawer when the "More" button is clicked
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                } else if (screen.route != currentDestination.route) {
                                    navController.navigate(screen.route)
                                }
                            } else {
                                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        ) { innerPadding  ->

            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Screen.SplashScreen.route
            ) {
                composable(Screen.SplashScreen.route) { SplashScreen(navController) }
                composable(Screen.TasksScreen.route) { TasksScreen(navController) }
                composable(Screen.CalenderScreen.route) { CalenderScreen(navController) }
                composable(Screen.ProfileScreen.route) { ProfileScreen(navController) }
                composable(Screen.OnboardingScreen.route) { OnboardingPager(navController) }
                composable(Screen.SignInScreen.route) { SignInScreen(navController) }
                composable(Screen.SignUpScreen.route) { SignUpScreen(navController) }
            }
        }
    }
}
