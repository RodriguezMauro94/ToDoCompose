package com.rodriguezmauro1994.todoapp.addtasks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    fun addTask(item: TaskEntity)

    @Update
    fun updateTask(item: TaskEntity)

    @Delete
    fun deleteTask(taskEntity: TaskEntity)
}