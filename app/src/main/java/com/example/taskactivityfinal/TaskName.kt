package com.example.taskactivityfinal

import androidx.compose.foundation.MutatePriority
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class TaskName(
    val taskTitle:String,
    val taskCategory: String,
    val taskContent:String,
    val taskPriority: String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)