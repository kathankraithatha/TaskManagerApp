package com.example.taskactivityfinal

import androidx.compose.foundation.MutatePriority

data class TaskState(
    val tasks:List<TaskName> = emptyList(),
    val taskTitle:String="",
    val taskCategory: String="",
    val taskContent:String="",
    val taskPriority:String="",
    val taskSort: TaskSort=TaskSort.TASK_TITLE
)
