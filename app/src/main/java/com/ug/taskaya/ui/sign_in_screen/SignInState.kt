package com.ug.taskaya.ui.sign_in_screen

import com.ug.taskaya.utils.AuthState

data class SignInState(

    val message : String,
    val launchedEffectKey : Boolean,
    val authState : AuthState,
    val email: String,
    val password: String
)
