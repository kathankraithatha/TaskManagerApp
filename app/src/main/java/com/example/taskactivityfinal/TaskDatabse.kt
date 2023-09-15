package com.example.taskactivityfinal

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskName::class],
    version = 2,
)
abstract class TaskDatabase:RoomDatabase() {
    abstract val dao:TaskDao
}