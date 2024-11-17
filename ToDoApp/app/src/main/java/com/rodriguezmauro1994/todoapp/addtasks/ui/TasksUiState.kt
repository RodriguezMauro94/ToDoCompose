package com.rodriguezmauro1994.todoapp.addtasks.ui

import com.rodriguezmauro1994.todoapp.addtasks.ui.model.TaskModel

sealed interface TasksUiState {
    data object Loading: TasksUiState
    data class Error (
        val throwable: Throwable
    ): TasksUiState
    data class Success(
        val tasks: List<TaskModel>
    ): TasksUiState
}