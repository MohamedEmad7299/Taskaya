package com.ug.taskaya.ui.sign_in_screen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject



@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: Repository,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _screenState = MutableStateFlow(

        SignInState(
            message = "",
            launchedEffectKey = false,
            authState = AuthState.Unauthenticated,
            email = "",
            password = ""
        )
    )

    val screenState = _screenState.asStateFlow()


    fun signIn(email: String, password: String) {

        _screenState.update { it.copy(authState = AuthState.Loading) }

        repository.signIn(email, password) { isSuccess, message ->
            _screenState.update {
                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    authState = if (isSuccess) AuthState.Authenticated else AuthState.Error,
                    message = message
                )
            }
        }
    }

    fun signOut() {

        _screenState.update { it.copy(authState = AuthState.Loading) }

        repository.signOut {
            _screenState.update {
                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    authState = AuthState.Unauthenticated
                )
            }
        }
    }

    fun onChangeEmail(newEmail : String){

        _screenState.update { it.copy(email = newEmail) }
    }

    fun onChangePassword(newPassword : String){

        _screenState.update { it.copy(password = newPassword) }
    }

    fun onInternetError(){
        _screenState.update { it.copy(
            message = "No Internet Connection",
            launchedEffectKey = !_screenState.value.launchedEffectKey) }
    }
}

