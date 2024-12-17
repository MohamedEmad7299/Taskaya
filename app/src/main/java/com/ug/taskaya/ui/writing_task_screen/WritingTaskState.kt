package com.ug.taskaya.ui.writing_task_screen

import com.ug.taskaya.data.entities.TaskEntity

data class WritingTaskState(

    val message : String,
    val launchedEffectKey : Boolean,
    val task: TaskEntity
)
