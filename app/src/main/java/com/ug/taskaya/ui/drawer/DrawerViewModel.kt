package com.ug.taskaya.ui.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _screenState = MutableStateFlow(

        DrawerState(
            message = "",
            launchedEffectKey = false,
            labels = emptyList()
        )
    )

    val screenState = _screenState.asStateFlow()

    init {
        fetchLabels()
    }

    private fun fetchLabels() {

        viewModelScope.launch {

            repository.listenToLabels{ result ->
                result.onSuccess { labels ->
                    SharedState.updateAllLabels(labels)
                }
            }
        }
    }

    fun updateLabels(labels: List<String>) {

        _screenState.update { it.copy(labels = labels) }
    }
}
