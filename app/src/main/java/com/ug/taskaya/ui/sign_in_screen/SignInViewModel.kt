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

    private val repository: Repository

) : ViewModel() {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _screenState = MutableStateFlow(

        SignInState(
            message = "",
            launchedEffectKey = false,
            isLoading = false,
            isEmailError = false,
            passwordVisibility = false,
            isPasswordError = false,
            authState = AuthState.Unauthenticated,
            email = "",
            password = ""
        )
    )

    val screenState = _screenState.asStateFlow()

    private fun checkAuthStatus(){

        if (auth.currentUser == null)
            _screenState.update { it.copy(authState =  AuthState.Unauthenticated) }
        else _screenState.update { it.copy(authState =  AuthState.Authenticated) }

    }


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

    fun a11(email: String,password: String){


        if (email.isEmpty() || password.isEmpty()){
            _screenState.update { it.copy(
                launchedEffectKey = !it.launchedEffectKey,
                authState = AuthState.Error,
                message = "Email or Password can't be empty"
            ) }
            return
        }

        _screenState.update { it.copy(authState = AuthState.Loading) }
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    _screenState.update { it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = AuthState.Authenticated,
                        message = "Welcome"
                    ) }
                else
                    _screenState.update { it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = AuthState.Error,
                        message = "Email or password is incorrect"
                    ) }
            }
    }


    fun signUp(email: String,password: String){

        if (email.isEmpty() || password.isEmpty()){
            _screenState.update { it.copy(
                launchedEffectKey = !it.launchedEffectKey,
                authState = AuthState.Error
            ) }
            return
        }

        _screenState.update { it.copy(authState = AuthState.Loading) }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    _screenState.update { it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = AuthState.Authenticated
                    ) }
                else
                    _screenState.update { it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = AuthState.Error
                    ) }
            }
    }

    fun signOut(){
        auth.signOut()
        _screenState.update { it.copy(
            launchedEffectKey = !it.launchedEffectKey,
            authState = AuthState.Unauthenticated
        ) }
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

