package com.ug.taskaya.ui.writing_task_screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.ui.theme.OffWhite
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


    private val currentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    private val _screenState = MutableStateFlow(

        WritingTaskState(
            message = "",
            launchedEffectKey = false,
            savingState = SavingState.Error,
            task = TaskEntity(
                id = System.currentTimeMillis(),
                labels = emptyList(),
                isRepeated = false,
                isStared = false,
                dueDate = currentDate,
                taskContent = "",
                priority = OffWhite.value.toLong(),
                isCompleted = false
            )
        )
    )

    val screenState = _screenState.asStateFlow()


    fun saveTask(navController: NavController) {

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

            val result = repository.addTaskToUser(currentUserEmail, taskToAdd)

            _screenState.update {
                it.copy(
                    launchedEffectKey = !it.launchedEffectKey,
                    message = if (result.isSuccess) "Task saved successfully" else result.exceptionOrNull()?.message ?: "Error to save task",
                    savingState = if (result.isSuccess) SavingState.Success else SavingState.Error
                )
            }

            if (result.isSuccess){
                SharedState.updateTasks(listOf(taskToAdd))
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

    suspend fun collectSelectedLabels(){

        SharedState.selectedLabels.collect { labels ->
            _screenState.update { it.copy(task = it.task.copy(labels = labels)) }
        }
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