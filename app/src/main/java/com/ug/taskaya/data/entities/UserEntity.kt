package com.ug.taskaya.data.entities

data class UserEntity(
    val id: Long,
    val name: String,
    val email: String,
    val tasks: List<TaskEntity>,
    val labels: List<String>
)