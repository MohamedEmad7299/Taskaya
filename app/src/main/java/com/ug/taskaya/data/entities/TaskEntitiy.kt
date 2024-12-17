package com.ug.taskaya.data.entities


data class TaskEntity(
    val taskContent: String,
    val labels: List<String>,
    val dueDate: String,
    val isRepeated: Boolean,
    val isStared: Boolean
)
