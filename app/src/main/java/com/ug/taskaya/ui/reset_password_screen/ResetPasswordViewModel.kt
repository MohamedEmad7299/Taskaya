package com.ug.taskaya.ui.reset_password_screen

import androidx.lifecycle.ViewModel
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject



@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
   private val repository: Repository
) : ViewModel() {


    private val _screenState = MutableStateFlow(

        ResetPasswordState(
            message = "",
            launchedEffectKey = false,
            isLoading = false,
            isEmailError = false,
            authState = AuthState.Unauthenticated,
            email = ""
        )
    )

    val screenState = _screenState.asStateFlow()


    fun sendPasswordResetEmail(email: String) {

        if (email.isEmpty()) {

            _screenState.update {

                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    authState = AuthState.Error,
                    message = "Email can't be empty"
                )
            }

            return
        }

        _screenState.update { it.copy(authState = AuthState.Loading) }

        repository.sendPasswordResetEmail(email) { result ->
            result.onSuccess {
                _screenState.update {
                    it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = AuthState.Unauthenticated,
                        message = "Password reset email sent successfully"
                    )
                }
            }.onFailure { exception ->
                _screenState.update {
                    it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = AuthState.Error,
                        message = exception.message ?: "Error to send reset email"
                    )
                }
            }
        }
    }

    fun onChangeEmail(newEmail : String){

        _screenState.update { it.copy(email = newEmail) }
    }
}