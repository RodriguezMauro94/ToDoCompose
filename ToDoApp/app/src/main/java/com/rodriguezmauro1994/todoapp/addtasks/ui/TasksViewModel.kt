package com.rodriguezmauro1994.todoapp.addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguezmauro1994.todoapp.addtasks.domain.AddTaskUseCase
import com.rodriguezmauro1994.todoapp.addtasks.domain.GetTasksUseCase
import com.rodriguezmauro1994.todoapp.addtasks.ui.TasksUiState.Error
import com.rodriguezmauro1994.todoapp.addtasks.ui.TasksUiState.Loading
import com.rodriguezmauro1994.todoapp.addtasks.ui.TasksUiState.Success
import com.rodriguezmauro1994.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    val getTasksUseCase: GetTasksUseCase
) : ViewModel() {
    val uiState: StateFlow<TasksUiState> = getTasksUseCase()
        .map(::Success)
        .catch {
            Error(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Loading
        )

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
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase.invoke(TaskModel(task = task))
        }
    }

    fun onItemChecked(task: TaskModel) {
        //FIXME
        /*val index = _myTasks.indexOf(task)
        _myTasks[index] = _myTasks[index].let {
            it.copy(selected = !it.selected)
        }*/
    }

    fun onItemRemoved(task: TaskModel) {
        //FIXME
        /*_myTasks.remove(_myTasks.find {
            it.id == task.id
        })*/
    }
}