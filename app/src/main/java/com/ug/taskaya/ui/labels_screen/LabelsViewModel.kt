package com.ug.taskaya.ui.labels_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ug.taskaya.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _screenState = MutableStateFlow(

        LabelsState(
            message = "",
            launchedEffectKey = false,
            labels = emptyList(),
            searchInput = ""
        )
    )

    val screenState = _screenState.asStateFlow()

    init {
        fetchLabels()
    }

    fun initializeLabels(labels: List<String>){
        _screenState.update { it.copy(labels = labels) }
    }


    fun fetchLabels() {

        viewModelScope.launch {
            val result = repository.fetchLabels()
            result.onSuccess { labels ->
                _screenState.update { it.copy(labels = labels) }
            }.onFailure { error ->
                _screenState.update {
                    it.copy(
                        message = error.localizedMessage ?: "Failed to fetch labels",
                        launchedEffectKey = !it.launchedEffectKey
                    )
                }
            }
        }
    }


    fun addLabel(name: String) {

        viewModelScope.launch {
            val result = repository.addLabel(name)
            result.onSuccess {
                fetchLabels()
            }.onFailure { error ->
                _screenState.update {
                    it.copy(
                        message = error.localizedMessage ?: "Failed to add label",
                        launchedEffectKey = !it.launchedEffectKey
                    )
                }
            }
        }

        onChangeSearchInput("")
    }

    fun onChangeSearchInput(searchInput: String){

        _screenState.update { it.copy(searchInput = searchInput) }
    }

    fun removeLabel(name: String) {

        viewModelScope.launch {
            val result = repository.removeLabel(name)
            result.onSuccess {
                fetchLabels() // Refresh the label list after removing
            }.onFailure { error ->
                _screenState.update {
                    it.copy(
                        message = error.localizedMessage ?: "Failed to remove label",
                        launchedEffectKey = !it.launchedEffectKey
                    )
                }
            }
        }
    }
}