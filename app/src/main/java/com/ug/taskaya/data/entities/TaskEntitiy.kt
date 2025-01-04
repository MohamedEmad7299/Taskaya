package com.ug.taskaya.data.entities

data class TaskEntity(
    val id: Long,
    val taskContent: String,
    val labels: List<String>,
    val dueDate: String,
    val isRepeated: Boolean,
    val completionDate: String,
    val isStared: Boolean,
    val priority: Long,
    val isCompleted: Boolean
)
