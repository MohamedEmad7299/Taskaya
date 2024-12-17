package com.ug.taskaya.ui.sign_up_screen

import androidx.lifecycle.ViewModel
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _screenState = MutableStateFlow(

        SignUpState(
            message = "",
            launchedEffectKey = false,
            authState = AuthState.Unauthenticated,
            email = "",
            password = "",
            name = "",
            rePassword = "",
        )
    )

    val screenState = _screenState.asStateFlow()



   private fun isSignUpReady(): Boolean{

        if (_screenState.value.password != _screenState.value.rePassword){

            _screenState.update { it.copy(authState = AuthState.Error ,
                message = "Password and Re-password must be identical",
                launchedEffectKey = !it.launchedEffectKey) }
            return false
        }

        if (_screenState.value.name.length < 3){

            _screenState.update { it.copy(authState = AuthState.Error ,
                message = "Name must be more than 2 characters",
                launchedEffectKey = !it.launchedEffectKey) }
            return false
        }

        return true
    }

    fun signUp(email: String, password: String) {

        if (isSignUpReady()){

            _screenState.update { it.copy(authState = AuthState.Loading) }

            repository.signUp(email, password) { isSuccess, message ->
                _screenState.update {
                    it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = if (isSuccess) AuthState.Authenticated else AuthState.Error,
                        message = message
                    )
                }
            }
        }
    }

    fun onChangeEmail(newEmail : String){

        _screenState.update { it.copy(email = newEmail) }
    }

    fun onChangePassword(newPassword : String){

        _screenState.update { it.copy(password = newPassword) }
    }

    fun onChangeRePassword(newPassword : String){

        _screenState.update { it.copy(rePassword = newPassword) }
    }

    fun onChangeName(name : String){

        _screenState.update { it.copy(name = name) }
    }

    fun onInternetError(){
        _screenState.update { it.copy(
            message = "No Internet Connection",
            launchedEffectKey = !_screenState.value.launchedEffectKey) }
    }

}