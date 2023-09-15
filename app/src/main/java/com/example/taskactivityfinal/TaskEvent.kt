package com.example.taskactivityfinal

sealed interface TaskEvent {
    object SaveTasks:TaskEvent
    data class SaveByTitle(val taskTitle: String):TaskEvent
    data class SaveByCategory(val taskCategory: String):TaskEvent
    data class SaveByContent(val taskContent: String):TaskEvent
    data class SaveByPriority(val taskPriority: String):TaskEvent
    data class SortTasks(val taskSort: TaskSort):TaskEvent
    data class DeleteTask(val task:TaskName):TaskEvent

}