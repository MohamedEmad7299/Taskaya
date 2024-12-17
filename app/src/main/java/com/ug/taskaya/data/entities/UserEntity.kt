package com.ug.taskaya.data.entities

data class UserEntity(

    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val tasks: List<TaskEntity>,
)