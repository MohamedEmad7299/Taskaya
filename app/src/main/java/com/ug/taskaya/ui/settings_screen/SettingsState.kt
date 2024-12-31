package com.ug.taskaya.ui.settings_screen

data class SettingsState(

    val message : String,
    val launchedEffectKey : Boolean,
    val signingOutState : SigningOutState,

)

sealed class SigningOutState{

    data object Success : SigningOutState()
    data object Loading : SigningOutState()
    data object Error : SigningOutState()
}
