package com.rodriguezmauro1994.todoapp.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodriguezmauro1994.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class TasksViewModel @Inject constructor(): ViewModel() {
    private var _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private var _myTasks = mutableStateListOf<TaskModel>()
    val myTasks: List<TaskModel> = _myTasks

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    fun onTaskAdded(task: String) {
        _myTasks.add(
            TaskModel(task = task)
        )
        onDialogDismiss()
    }

    fun onItemChecked(task: TaskModel) {
        val index = _myTasks.indexOf(task)
        _myTasks[index] = _myTasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemoved(task: TaskModel) {
        _myTasks.remove(_myTasks.find {
            it.id == task.id
        })
    }
}