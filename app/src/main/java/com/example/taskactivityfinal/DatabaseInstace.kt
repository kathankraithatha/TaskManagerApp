package com.example.taskactivityfinal

import android.content.Context
import androidx.room.Room

object TaskDatabaseSingleton {
    private var instance: TaskDatabase? = null

    fun getInstance(context: Context): TaskDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "taskActivity.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}