package com.ug.taskaya.ui.writing_task_screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


object SharedState {

    private val _sharedLabels = MutableStateFlow<List<String>>(emptyList())
    val sharedLabels = _sharedLabels.asStateFlow()

    fun updateLabels(newLabels: List<String>) {
        _sharedLabels.value = newLabels
    }
}