package com.rodriguezmauro1994.todoapp.addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TasksViewModel @Inject constructor(): ViewModel() {
    private var _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun onShowDialog() {
        _showDialog.value = true
    }

    fun onTaskAdded(task: String) {
        onDialogDismiss()
        //TODO save task
    }
}