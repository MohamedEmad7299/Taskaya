package com.ug.taskaya.ui.labels_screen

data class LabelsState(

    val searchInput: String,
    val labels: List<String>,
    val message : String,
    val launchedEffectKey : Boolean,
    val selectedLabels: List<String>
)
