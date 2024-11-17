package com.rodriguezmauro1994.todoapp.addtasks.data

import com.rodriguezmauro1994.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDAO: TaskDAO
) {
    val tasks: Flow<List<TaskModel>> = taskDAO.getTasks().map { items ->
        items.map {
            TaskModel(
                it.id,
                it.task,
                it.selected
            )
        }
    }

    suspend fun add(taskModel: TaskModel) {
        taskDAO.addTask(
            TaskEntity(
                taskModel.id,
                taskModel.task,
                taskModel.selected
            )
        )
    }
}