package com.ug.taskaya.utils

sealed class AuthState{

    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data object Error : AuthState()
}