package com.ug.taskaya.ui.stared_tasks_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StaredTasksViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){


    private val _screenState = MutableStateFlow(

        StaredTasksState(
            message = "",
            launchedEffectKey = false,
            staredTasks = emptyList()
        )
    )

    val screenState = _screenState.asStateFlow()

    init {
        startListeningToTasks()
    }

    private fun updateStaredTasks(staredTasks: List<TaskEntity>){
        _screenState.update { it.copy(staredTasks = staredTasks) }
    }

    fun deleteTask(taskID: Long){

        updateStaredTasks(
            _screenState.value.staredTasks.filter { it.id != taskID }
        )

        viewModelScope.launch {
            val result = repository.deleteTaskForCurrentUser(taskID)
            result.onFailure {
                _screenState.update { it.copy(launchedEffectKey = !it.launchedEffectKey,
                    message = "Something went wrong") }
            }
        }
    }

    private fun startListeningToTasks() {
        repository.listenToTasks{ result ->

            result.onSuccess { tasks ->

                _screenState.update { state ->
                    state.copy(staredTasks = tasks.filter { task ->
                        task.isStared }
                    )
                }
            }
        }
    }

    fun updateTask(task: TaskEntity){

        updateStaredTasks(
            _screenState.value.staredTasks.filter { it.id != task.id }
        )

        viewModelScope.launch {
            val result = repository.updateTaskForCurrentUser(task)
            result.onFailure {
                _screenState.update { it.copy(launchedEffectKey = !it.launchedEffectKey,
                    message = "Something went wrong") }
            }
        }
    }
}