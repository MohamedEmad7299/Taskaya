package com.ug.taskaya.ui.stared_tasks_screen

import com.ug.taskaya.data.entities.TaskEntity

data class StaredTasksState(

    val message : String,
    val launchedEffectKey : Boolean,
    val staredTasks: List<TaskEntity>
)
