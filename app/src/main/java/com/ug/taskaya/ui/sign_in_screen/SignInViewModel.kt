package com.ug.taskaya.ui.sign_in_screen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.AuthState
import com.ug.taskaya.utils.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject



@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: Repository
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

    fun navigateTo(
        navController: NavController,
        route: String,
        clearStack: Boolean = false
    ){
        navController.navigate(route) {
            if (clearStack) {
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
        }
    }

    suspend fun addNewUserToFireStore(){
        if (!userInFireStore())
        repository.addNewUserToFireStore()
    }

    private suspend fun userInFireStore(): Boolean{

        return  repository.fetchUserByEmail().isSuccess
    }

    fun signIn(email: String, password: String , navController: NavController) {

        _screenState.update { it.copy(authState = AuthState.Loading) }

        repository.signIn(email, password) { isSuccess, message ->

            if (isSuccess) {

                _screenState.update {

                    it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        authState = AuthState.Authenticated,
                        message = message
                    )
                }

                navigateTo(navController,Screen.TasksScreen.route,true)

            } else {

                _screenState.update {
                    it.copy(
                        authState = AuthState.Error,
                        message = message,
                        launchedEffectKey = !it.launchedEffectKey
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
}

