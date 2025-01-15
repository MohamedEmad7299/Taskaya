package com.ug.taskaya.ui.tasks_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){


    private val _screenState = MutableStateFlow(

        TasksState(
            message = "",
            launchedEffectKey = false,
            completedTasks = emptyList(),
            taskOnHold = TaskEntity(
                id = 0,
                taskContent = "",
                labels = emptyList(),
                dueDate = "",
                isRepeated = false,
                isStared = false,
                priority = 0,
                isCompleted = false,
                completionDate = ""
            )
        )
    )

    val screenState = _screenState.asStateFlow()

    init {
        startListeningToTasks()
    }

    fun updateTaskOnHold(task: TaskEntity){
        _screenState.update { it.copy(taskOnHold = task) }
    }


    fun deleteTask(task: TaskEntity){

        viewModelScope.launch {
            val result = repository.deleteTaskForCurrentUser(task.id)
            result.onFailure {
                _screenState.update { it.copy(launchedEffectKey = !it.launchedEffectKey,
                    message = "Something went wrong") }
            }
        }
    }

    private fun startListeningToTasks() {

        repository.listenToTasks{ result ->

            result.onSuccess { tasks ->
                SharedState.updateTasks(tasks)
            }
        }
    }

    fun updateTask(task: TaskEntity){

        viewModelScope.launch {
            val result = repository.updateTaskForCurrentUser(task)
            result.onFailure {
                _screenState.update { it.copy(launchedEffectKey = !it.launchedEffectKey,
                    message = "Something went wrong") }
            }
        }
    }

    fun prepareForNewTask(){

        SharedState.updateOnEditTask(
            TaskEntity(
                id = System.currentTimeMillis(),
                taskContent = "",
                isRepeated = false,
                isCompleted = false,
                dueDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                labels = emptyList(),
                priority = 0,
                completionDate = "",
                isStared = false
            )
        )
    }
}