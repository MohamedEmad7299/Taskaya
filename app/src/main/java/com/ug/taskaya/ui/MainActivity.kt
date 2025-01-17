package com.ug.taskaya.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ug.taskaya.data.repositories.FacebookSignInAuth
import com.ug.taskaya.ui.composables.PieeChart
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var facebookSignInAuth: FacebookSignInAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        facebookSignInAuth = FacebookSignInAuth(this)

        setContent {
            TaskayaApp(facebookSignInAuth)
            //PieeChart()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookSignInAuth.onActivityResult(requestCode, resultCode, data)
    }
}
