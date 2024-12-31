package com.ug.taskaya.ui.delete_label_screen

data class DeleteLabelsState(
    val labels: List<String>,
    val message : String,
    val launchedEffectKey : Boolean,
    val searchInput: String
)
