package com.ug.taskaya.ui.profile_screen

import androidx.lifecycle.ViewModel
import com.ug.taskaya.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _screenState = MutableStateFlow(

        ProfileState(
            name = "",
            message = "",
            launchedEffectKey = false
        )
    )

    val screenState = _screenState.asStateFlow()

    init {
        _screenState.update { it.copy(
            name = repository.getCurrentUserName() ?: "")
        }
    }
}