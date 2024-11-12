package com.ug.taskaya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.ug.taskaya.ui.TaskayaApp
import com.ug.taskaya.ui.calendar_screen.CalenderScreen
import com.ug.taskaya.ui.details_screen.DetailsScreen
import com.ug.taskaya.ui.profile_screen.ProfileContent
import com.ug.taskaya.ui.settings.SettingsScreen
import com.ug.taskaya.ui.sign_in_screen.SignInScreen
import com.ug.taskaya.ui.sign_up_screen.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DetailsScreen()
        }
    }
}