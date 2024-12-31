package com.ug.taskaya.ui.tasks_screen

import androidx.lifecycle.ViewModel
import com.ug.taskaya.data.repositories.Repository
import com.ug.taskaya.utils.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){


    private val _screenState = MutableStateFlow(

        TasksState(
            message = "",
            launchedEffectKey = false,
            tasks = emptyList(),
            labels = emptyList(),
            completedTasks = emptyList()
        )
    )

    val screenState = _screenState.asStateFlow()

    init {

        clearSelectedTasks()
    }


    private fun clearSelectedTasks(){

        SharedState.updateSelectedLabels(emptyList())
    }
}