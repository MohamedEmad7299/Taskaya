package com.ug.taskaya.utils

import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.ui.theme.OffWhite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter


object SharedState {

    private val _selectedLabels = MutableStateFlow<List<String>>(emptyList())
    val selectedLabels = _selectedLabels.asStateFlow()

    private val _labels = MutableStateFlow<List<String>>(emptyList())
    val labels = _labels.asStateFlow()

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _onEditTask = MutableStateFlow(TaskEntity(
        taskContent = "",
        isStared = false,
        id = System.currentTimeMillis(),
        isCompleted = false,
        dueDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        isRepeated = false,
        labels = emptyList(),
        priority = OffWhite.value.toLong(),
        completionDate = ""
    ))
    val onEditTask = _onEditTask.asStateFlow()

    private val _currentLabel = MutableStateFlow("All")
    val currentLabel = _currentLabel.asStateFlow()

    fun updateCurrentLabel(label: String) {
        _currentLabel.value = label
    }

    fun updateOnEditTask(newTask: TaskEntity) {
        _onEditTask.value = newTask
    }

    fun updateTasks(newTasks: List<TaskEntity>) {
        _tasks.value = newTasks
    }

    fun updateSelectedLabels(newLabels: List<String>) {
        _selectedLabels.value = newLabels
    }

    fun updateAllLabels(newLabels: List<String>) {
        _labels.value = newLabels
    }
}