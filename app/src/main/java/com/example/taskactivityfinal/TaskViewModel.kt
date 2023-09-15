package com.example.taskactivityfinal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val dao: TaskDao
):ViewModel() {
    private val _sortType= MutableStateFlow(TaskSort.TASK_TITLE)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _tasks=_sortType
        .flatMapLatest { sortType ->
            when(sortType){
                TaskSort.TASK_TITLE -> dao.getTaskByTitle()
                TaskSort.TASK_CATEGORY -> dao.getTaskByCategory()
                TaskSort.TASK_CONTENT -> dao.getTaskByContent()
                TaskSort.TASK_PRIORITY -> dao.getTaskByPriority()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state= MutableStateFlow(TaskState())
    val state= combine(_state,_sortType,_tasks){ state,sortType,tasks ->
        state.copy(
            taskSort = sortType,
            tasks = tasks
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TaskState())


    fun onEvent(Event:TaskEvent){
        when(Event){
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    dao.deleteTasks(Event.task)
                }
            }
            TaskEvent.SaveTasks -> {
                val taskTitle=state.value.taskTitle
                val taskContent=state.value.taskContent
                val taskCategory=state.value.taskCategory
                val taskPriority=state.value.taskPriority
                if(taskContent.isBlank() || taskCategory.isBlank() || taskTitle.isBlank() || taskPriority.isBlank()){
                    return
                }
                val tasks=TaskName(
                    taskTitle=taskTitle,
                    taskContent = taskContent,
                    taskCategory = taskCategory,
                    taskPriority = taskPriority
                )
                viewModelScope.launch {
                    dao.updateTasks(tasks)
                }
                _state.update { it.copy(
                    taskTitle = "",
                    taskCategory = "",
                    taskContent = "",
                    taskPriority = ""
                ) }

            }
            is TaskEvent.SaveByCategory -> {
                _state.update { it.copy(
                    taskCategory =Event.taskCategory
                ) }
            }
            is TaskEvent.SaveByContent -> {
                _state.update { it.copy(
                    taskContent = Event.taskContent
                ) }
            }
            is TaskEvent.SaveByTitle -> {
                _state.update { it.copy(
                    taskTitle = Event.taskTitle
                ) }
            }
            is TaskEvent.SortTasks -> {
                _sortType.value=Event.taskSort
            }

            is TaskEvent.SaveByPriority -> {
                _state.update { it.copy(
                    taskPriority = Event.taskPriority
                ) }
            }

        }
    }
}