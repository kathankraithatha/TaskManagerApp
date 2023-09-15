package com.example.taskactivityfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class SecondActivity: ComponentActivity() {
     private val db:TaskDatabase by lazy {
         TaskDatabaseSingleton.getInstance(applicationContext)
     }
    private val viewModel by viewModels<TaskViewModel> {
        TaskViewModelFactory(db.dao)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            DetailedTasks(state=state, onEvent = viewModel::onEvent)
        }
    }
}