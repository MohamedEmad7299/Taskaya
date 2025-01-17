package com.ug.taskaya.ui.settings_screen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){


    private val _screenState = MutableStateFlow(

        SettingsState(
            message = "",
            launchedEffectKey = false,
            signingOutState = SigningOutState.Error,
        )
    )

    val screenState = _screenState.asStateFlow()


    fun signOut(navController: NavController) {

        _screenState.update { it.copy(signingOutState = SigningOutState.Loading) }

        repository.signOut { result ->

            result.fold(

                onSuccess = {

                    _screenState.update {
                        it.copy(
                            launchedEffectKey = !it.launchedEffectKey,
                            signingOutState = SigningOutState.Success,
                            message = "Sign out successful."
                        )
                    }

                    navController.navigate(Screen.SignInScreen.route){
                        popUpTo(navController.graph.id){
                            inclusive = true }
                    }
                },
                onFailure = { exception ->
                    _screenState.update {
                        it.copy(
                            signingOutState = SigningOutState.Error,
                            message = "Sign out failed: ${exception.localizedMessage}"
                        )
                    }
                }
            )
        }
    }
}


