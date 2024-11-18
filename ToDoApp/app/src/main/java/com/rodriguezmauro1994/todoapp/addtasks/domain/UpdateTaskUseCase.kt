package com.rodriguezmauro1994.todoapp.addtasks.domain

import com.rodriguezmauro1994.todoapp.addtasks.data.TaskRepository
import com.rodriguezmauro1994.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskModel: TaskModel) {
        repository.update(taskModel)
    }
}