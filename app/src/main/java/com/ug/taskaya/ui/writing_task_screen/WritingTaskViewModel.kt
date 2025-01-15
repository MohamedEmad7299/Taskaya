package com.ug.taskaya.ui.writing_task_screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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
class WritingTaskViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _screenState = MutableStateFlow(

        WritingTaskState(
            message = "",
            launchedEffectKey = false,
            savingState = SavingState.Error,
            task = SharedState.onEditTask.value,
            tasks = emptyList()
        )
    )

    val screenState = _screenState.asStateFlow()


    init {
        SharedState.updateSelectedLabels(_screenState.value.task.labels)
        fetchTasks()
    }

    private fun fetchTasks() {

        viewModelScope.launch {

            repository.listenToTasks{ result ->
                result.onSuccess { tasks ->
                    _screenState.update { it.copy(tasks = tasks) }
                    SharedState.updateTasks(tasks)
                }
            }
        }
    }


    fun onChangeTaskLabels(
        labels: List<String>
    ){
        _screenState.update { it.copy(
            task = it.task.copy(
                labels = labels
            )
        ) }
    }

    fun isTaskExist(tasks: List<TaskEntity>):Boolean {
        return tasks.any{ it.id == _screenState.value.task.id }
    }

    fun updateTask(navController : NavController){

        if (_screenState.value.task.taskContent.isBlank()) {

            _screenState.update {
                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    message = "Task content cannot be empty"
                )
            }

            return
        }

        viewModelScope.launch {

            val result = repository.updateTaskForCurrentUser(_screenState.value.task.copy(labels = SharedState.selectedLabels.value))

            result.onFailure {
                _screenState.update { it.copy(launchedEffectKey = !it.launchedEffectKey,
                    message = "Something went wrong") }
            }.onSuccess {

                _screenState.update {
                    it.copy(
                        launchedEffectKey = !it.launchedEffectKey,
                        message = "Task saved successfully",
                        savingState = SavingState.Success
                    )
                }

                updateSharedState()
                navController.popBackStack()
            }
        }
    }

    private fun updateSharedState(){

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

        SharedState.updateSelectedLabels(emptyList())
    }

    fun saveTask(navController: NavController) {

        if (_screenState.value.task.taskContent.isBlank()) {
            _screenState.update {
                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    message = "Task content cannot be empty"
                )
            }
            return
        }

        _screenState.update { it.copy( savingState = SavingState.Loading) }

        val taskToAdd = _screenState.value.task
        val currentUserEmail = repository.getCurrentUserEmail()

        if (currentUserEmail.isNullOrEmpty()) {
            _screenState.update {
                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    message = "No user signed in"
                )
            }
            return
        }

        viewModelScope.launch {

            val result = repository.addTaskToUser(currentUserEmail, taskToAdd.copy(labels = SharedState.selectedLabels.value))

            _screenState.update {
                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    message = if (result.isSuccess) "Task saved successfully" else result.exceptionOrNull()?.message ?: "Error to save task",
                    savingState = if (result.isSuccess) SavingState.Success else SavingState.Error
                )
            }

            if (result.isSuccess){
                updateSharedState()
                navController.popBackStack()
            }
        }
    }

    fun updatePriority(newColor: Color){
        _screenState.update { it.copy(
            task = it.task.copy(
                priority = newColor.value.toLong()
            )
        ) }
    }

    fun onChangeTaskContent(
        taskContent: String
    ){
        _screenState.update { it.copy(
            task = it.task.copy(
                taskContent = taskContent
            )
        ) }
    }

    fun onChangeTaskDueDate(
        dueDate: String
    ){
        _screenState.update { it.copy(
            task = it.task.copy(
                dueDate = dueDate
            )
        ) }
    }

    fun onChangeRepetitionState(){
        _screenState.update { it.copy(
            task = it.task.copy(
                isRepeated = !it.task.isRepeated
            )
        ) }
    }

    fun onChangeStaredState(){
        _screenState.update { it.copy(
            task = it.task.copy(
                isStared = !it.task.isStared
            )
        ) }
    }
}