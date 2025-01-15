package com.ug.taskaya.ui.tasks_screen

import com.ug.taskaya.data.entities.TaskEntity

data class TasksState(
    val taskOnHold: TaskEntity,
    val message : String,
    val launchedEffectKey : Boolean,
    val completedTasks: List<TaskEntity>
)
