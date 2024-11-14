package com.ug.taskaya.ui.sign_in_screen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject



@HiltViewModel
class SignInViewModel @Inject constructor(
//    private val repository: Repository

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

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){

        if (auth.currentUser == null)
            _screenState.update { it.copy(authState =  AuthState.Unauthenticated) }
        else _screenState.update { it.copy(authState =  AuthState.Authenticated) }

    }


    fun signIn(email: String,password: String){


        if (email.isEmpty() || password.isEmpty()){
            _screenState.update { it.copy(
                authState = AuthState.Error("Email or Password can't be empty"),
                message = "Email or Password can't be empty"
            ) }
            return
        }

        _screenState.update { it.copy(authState = AuthState.Loading) }
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    _screenState.update { it.copy(
                        authState = AuthState.Authenticated,
                        message = "done"
                    ) }
                else
                    _screenState.update { it.copy(
                        authState = AuthState.Error(task.exception?.message?: "Something went wrong"),
                        message = "Something went wrong"
                    ) }
            }
    }


    fun signUp(email: String,password: String){


        if (email.isEmpty() || password.isEmpty()){
            _screenState.update { it.copy(authState = AuthState.Error("Email or Password can't be empty")) }
            return
        }

        _screenState.update { it.copy(authState = AuthState.Loading) }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    _screenState.update { it.copy(authState = AuthState.Authenticated) }
                else
                    _screenState.update { it.copy(authState = AuthState.Error(task.exception?.message?: "Something went wrong")) }
            }
    }

    fun signOut(){
        auth.signOut()
        _screenState.update { it.copy(authState = AuthState.Unauthenticated) }
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

sealed class AuthState{

    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}