package com.ug.taskaya.ui.sign_up_screen

import com.ug.taskaya.utils.AuthState

data class SignUpState(

    val message : String,
    val launchedEffectKey : Boolean,
    val authState : AuthState,
    val name: String,
    val email: String,
    val password: String,
    val rePassword: String,
)
