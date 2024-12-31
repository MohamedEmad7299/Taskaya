package com.ug.taskaya.utils

import com.ug.taskaya.data.entities.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


object SharedState {

    private val _selectedLabels = MutableStateFlow<List<String>>(emptyList())
    val selectedLabels = _selectedLabels.asStateFlow()

    private val _allLabels = MutableStateFlow<List<String>>(emptyList())
    val allLabels = _allLabels.asStateFlow()

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks = _tasks.asStateFlow()




    fun updateTasks(newTasks: List<TaskEntity>) {
        _tasks.value += newTasks
    }

    fun updateSelectedLabels(newLabels: List<String>) {
        _selectedLabels.value = newLabels
    }

    fun updateAllLabels(newLabels: List<String>) {
        _allLabels.value = newLabels
    }
}