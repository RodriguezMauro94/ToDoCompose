package com.rodriguezmauro1994.todoapp.addtasks.data.di

import android.content.Context
import androidx.room.Room
import com.rodriguezmauro1994.todoapp.addtasks.data.TaskDAO
import com.rodriguezmauro1994.todoapp.addtasks.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context) : TodoDatabase {
        return Room.databaseBuilder(appContext, TodoDatabase::class.java, "TaskDatabase").build()
    }

    @Provides
    fun providesTaskDao(todoDatabase: TodoDatabase): TaskDAO {
        return todoDatabase.taskDao()
    }
}