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
    import com.ug.taskaya.ui.delete_label_screen.DeleteLabelsScreen
    import com.ug.taskaya.ui.design_matrials.bottomNav.BottomNavScreen
    import com.ug.taskaya.ui.design_matrials.bottomNav.BottomNavigationBar
    import com.ug.taskaya.ui.drawer.DrawerScreen
    import com.ug.taskaya.ui.labels_screen.LabelsScreen
    import com.ug.taskaya.ui.no_internet_screen.NoInternetScreen
    import com.ug.taskaya.ui.onboarding_screen.OnboardingPager
    import com.ug.taskaya.ui.profile_screen.ProfileScreen
    import com.ug.taskaya.ui.reset_password_screen.ResetPasswordScreen
    import com.ug.taskaya.ui.settings_screen.SettingsScreen
    import com.ug.taskaya.data.repositories.FacebookSignInAuth
    import com.ug.taskaya.ui.sign_in_screen.SignInScreen
    import com.ug.taskaya.ui.sign_up_screen.SignUpScreen
    import com.ug.taskaya.ui.splash_screen.SplashScreen
    import com.ug.taskaya.ui.stared_tasks_screen.StaredTasksScreen
    import com.ug.taskaya.ui.tasks_screen.TasksScreen
    import com.ug.taskaya.ui.writing_task_screen.WritingTaskScreen
    import com.ug.taskaya.utils.Screen
    import com.ug.taskaya.utils.isInternetConnected
    
    @Composable
    fun TaskayaApp(
        facebookSignInAuth: FacebookSignInAuth
    ) {
    
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
    
                DrawerScreen(navController)
            }
        ){
    
            Scaffold(
    
                bottomBar = {
                    if (currentDestination?.route in bottomBarScreens) {
                        BottomNavigationBar(
                            currentScreen = currentDestination!!,
                            onNavigate = { screen ->
                                if (isInternetConnected(context)) {
                                    if (screen.route == BottomNavScreen.More.route) {
    
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

            ){ innerPadding  ->
    
                NavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    startDestination = Screen.SplashScreen.route
                ){
                    composable(Screen.SplashScreen.route) { SplashScreen(navController) }
                    composable(Screen.TasksScreen.route) { TasksScreen(navController) }
                    composable(Screen.CalenderScreen.route) { CalenderScreen(navController) }
                    composable(Screen.ProfileScreen.route) { ProfileScreen(navController) }
                    composable(Screen.OnboardingScreen.route) { OnboardingPager(navController) }
                    composable(Screen.SignInScreen.route) { SignInScreen(navController, facebookSignInAuth = facebookSignInAuth) }
                    composable(Screen.SignUpScreen.route) { SignUpScreen(navController, facebookSignInAuth = facebookSignInAuth) }
                    composable(Screen.ResetPasswordScreen.route) { ResetPasswordScreen(navController) }
                    composable(Screen.NoInternetScreen.route){ NoInternetScreen(navController) }
                    composable(Screen.WritingTaskScreen.route){ WritingTaskScreen(navController) }
                    composable(Screen.LabelsScreen.route){ LabelsScreen(navController) }
                    composable(Screen.TasksScreen.route){ TasksScreen(navController) }
                    composable(Screen.DrawerScreen.route){ DrawerScreen(navController) }
                    composable(Screen.SettingsScreen.route){ SettingsScreen(navController) }
                    composable(Screen.DeleteLabelsScreen.route){ DeleteLabelsScreen(navController) }
                    composable(Screen.StaredTasksScreen.route){ StaredTasksScreen(navController) }
                }
            }
        }
    }
