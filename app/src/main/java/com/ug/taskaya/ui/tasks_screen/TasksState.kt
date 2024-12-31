package com.ug.taskaya.ui.tasks_screen

import com.ug.taskaya.data.entities.TaskEntity

data class TasksState(

    val message : String,
    val launchedEffectKey : Boolean,
    val tasks: List<TaskEntity>,
    val labels: List<String>,
    val completedTasks: List<TaskEntity>
)
