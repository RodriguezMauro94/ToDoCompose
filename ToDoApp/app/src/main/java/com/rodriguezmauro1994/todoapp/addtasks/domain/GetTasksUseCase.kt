package com.rodriguezmauro1994.todoapp.addtasks.domain

import com.rodriguezmauro1994.todoapp.addtasks.data.TaskRepository
import com.rodriguezmauro1994.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val todoRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<TaskModel>> = todoRepository.tasks
}