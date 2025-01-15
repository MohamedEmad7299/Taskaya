package com.ug.taskaya.ui.labels_screen

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
class LabelsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _screenState = MutableStateFlow(

        LabelsState(
            message = "",
            launchedEffectKey = false,
            labels = emptyList(),
            searchInput = "",
            selectedLabels = emptyList()
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
                    _screenState.update { it.copy(labels = labels) }
                    SharedState.updateAllLabels(labels)
                }
            }
        }
    }

    fun updateSelectedLabels(labels :List<String>){

        _screenState.update {
            it.copy(
                selectedLabels = labels
            )
        }
    }

    fun changeCheckState(label: String){

        _screenState.update { it.copy(
            selectedLabels = it.selectedLabels.toMutableList().apply {
                if (!contains(label)) add(label)
                else remove(label)
            }
        ) }

        SharedState.updateSelectedLabels(_screenState.value.selectedLabels)
    }

    fun addLabel(labelName: String) {

        _screenState.update {
            it.copy(labels = it.labels+labelName)
        }

        viewModelScope.launch {

            val result = repository.addLabel(labelName)

            result.onFailure { error ->
                _screenState.update {
                    it.copy(
                        message = error.localizedMessage ?: "Something went wrong",
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
}