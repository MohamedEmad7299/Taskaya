package com.ug.taskaya

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.ug.taskaya.ui.TaskayaApp
import com.ug.taskaya.ui.sign_in_screen.FacebookSignInAuth
import com.ug.taskaya.ui.writing_task_screen.WritingTaskScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var facebookSignInAuth: FacebookSignInAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        facebookSignInAuth = FacebookSignInAuth(this)

        setContent {
            TaskayaApp(facebookSignInAuth)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookSignInAuth.onActivityResult(requestCode, resultCode, data)
    }
}
