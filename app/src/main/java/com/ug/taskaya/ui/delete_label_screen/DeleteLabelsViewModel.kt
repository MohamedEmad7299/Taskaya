package com.ug.taskaya.ui.delete_label_screen

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
class DeleteLabelsViewModel @Inject constructor(
    private val repository: Repository,
): ViewModel(){

    private val _screenState = MutableStateFlow(

        DeleteLabelsState(
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

    fun removeLabel(labelName: String) {

        _screenState.update {
            it.copy( labels = it.labels - labelName)
        }

        viewModelScope.launch {

            repository.removeLabelFromUser(labelName).fold(
                onSuccess = { fetchLabels() },
                onFailure = { error ->
                    _screenState.update { state ->
                        state.copy(
                            message = error.localizedMessage ?: "Failed to remove label",
                            launchedEffectKey = !state.launchedEffectKey
                        )
                    }
                }
            )
        }
    }

    fun onChangeSearchInput(searchInput: String){

        _screenState.update { it.copy(searchInput = searchInput) }
    }
}
