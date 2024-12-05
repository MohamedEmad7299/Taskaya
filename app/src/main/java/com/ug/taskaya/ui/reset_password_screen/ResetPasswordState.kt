package com.ug.taskaya.ui.reset_password_screen

import com.ug.taskaya.utils.AuthState


data class ResetPasswordState(

    val message : String,
    val launchedEffectKey : Boolean,
    val isLoading : Boolean,
    val isEmailError : Boolean,
    val email: String,
    val authState : AuthState,
)
