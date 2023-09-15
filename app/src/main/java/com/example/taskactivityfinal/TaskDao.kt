package com.example.taskactivityfinal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun updateTasks(task:TaskName)
    @Delete
    suspend fun deleteTasks(task: TaskName)
    @Query("SELECT * FROM TaskName ORDER BY taskTitle ")
    fun getTaskByTitle():Flow<List<TaskName>>
    @Query("SELECT * FROM TaskName ORDER BY taskContent ")
    fun getTaskByContent():Flow<List<TaskName>>
    @Query("SELECT * FROM TaskName ORDER BY taskCategory ")
    fun getTaskByCategory():Flow<List<TaskName>>
    @Query("SELECT * FROM TaskName ORDER BY taskPriority ")
    fun getTaskByPriority():Flow<List<TaskName>>
}