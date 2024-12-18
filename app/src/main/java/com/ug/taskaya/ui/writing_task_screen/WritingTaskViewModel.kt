package com.ug.taskaya.ui.writing_task_screen

import androidx.lifecycle.ViewModel
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            task = TaskEntity(
                labels = emptyList(),
                isRepeated = false,
                isStared = false,
                dueDate = currentDate,
                taskContent = ""
            )
        )
    )

    val screenState = _screenState.asStateFlow()


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

        SharedState.sharedLabels.collect { labels ->
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