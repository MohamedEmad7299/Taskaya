package com.ug.taskaya.ui.sign_up_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ug.taskaya.data.entities.UserEntity
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private fun isSignUpReady(): Boolean {

        if (_screenState.value.password != _screenState.value.rePassword &&
            _screenState.value.name.length > 2 &&
            _screenState.value.name.isNotBlank()) {
            _screenState.update {

                it.copy(
                    authState = AuthState.Error,
                    message = "Password and Re-password must be identical",
                    launchedEffectKey = !it.launchedEffectKey
                )
            }

            return false
        }

        if (_screenState.value.name.length < 3) {

            _screenState.update {
                it.copy(
                    authState = AuthState.Error,
                    message = "Name must be more than 2 characters",
                    launchedEffectKey = !it.launchedEffectKey
                )
            }

            return false
        }

        return true
    }

    suspend fun addNewUserToFireStore(): Boolean{

        return  repository.addNewUserToFireStore().isSuccess
    }

    fun navigateTo(
        navController: NavController ,
        route: String ,
        clearStack: Boolean = false){

        navController.navigate(route){
            if (clearStack)
                popUpTo(navController.graph.id){
                    inclusive = true }
        }
    }

    fun signUp(onSuccess: () -> Unit) {

        if (isSignUpReady()) {

            _screenState.update { it.copy(authState = AuthState.Loading) }

            repository.signUp(_screenState.value.email, _screenState.value.password , _screenState.value.name) { isSuccess, message ->

                if (isSuccess) {

                    val user = UserEntity(

                        id = System.currentTimeMillis(),
                        name = _screenState.value.name,
                        email = _screenState.value.email,
                        tasks = emptyList(),
                        labels = emptyList()
                    )

                    viewModelScope.launch {

                        val result = repository.addUser(user)
                        if (result.isSuccess) {

                            _screenState.update {
                                it.copy(
                                    authState = AuthState.Authenticated,
                                    message = "Account Created Successfully",
                                    launchedEffectKey = !it.launchedEffectKey
                                )
                            }

                            onSuccess()

                        } else {
                            _screenState.update {
                                it.copy(
                                    authState = AuthState.Error,
                                    message = result.exceptionOrNull()?.message
                                        ?: "Error to save user data",
                                    launchedEffectKey = !it.launchedEffectKey
                                )
                            }
                        }
                    }

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
    }

    fun onChangeEmail(newEmail: String) {
        _screenState.update { it.copy(email = newEmail) }
    }

    fun onChangePassword(newPassword: String) {
        _screenState.update { it.copy(password = newPassword) }
    }

    fun onChangeRePassword(newPassword: String) {
        _screenState.update { it.copy(rePassword = newPassword) }
    }

    fun onChangeName(name: String) {
        _screenState.update { it.copy(name = name) }
    }
}
