package com.rodriguezmauro1994.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.rodriguezmauro1994.todoapp.addtasks.ui.TasksUiState
import com.rodriguezmauro1994.todoapp.addtasks.ui.TasksViewModel
import com.rodriguezmauro1994.todoapp.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(initial = false)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<TasksUiState>(initialValue = TasksUiState.Loading,
        key1 = lifecycle,
        key2 = tasksViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tasksViewModel.uiState.collect {
                value = it
            }
        }
    }

    when(uiState){
        is TasksUiState.Error -> {
            //TODO do something
        }
        TasksUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is TasksUiState.Success -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                TasksList(tasksViewModel, (uiState as TasksUiState.Success).tasks)
                AddTasksDialog(show = showDialog, onDismiss = {
                    tasksViewModel.onDialogDismiss()
                }, onTaskAdded = {
                    tasksViewModel.onTaskAdded(it)
                })
                FabDialog(tasksViewModel, modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
    }
}

@Composable
fun TasksList(tasksViewModel: TasksViewModel, tasks: List<TaskModel>) {
    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            ItemTask(tasksViewModel, task)
        }
    }
}

@Composable
fun ItemTask(tasksViewModel: TasksViewModel, taskModel: TaskModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onItemRemoved(taskModel)
                })
            },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = taskModel.task,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                fontSize = 20.sp,
                color = Color.DarkGray
            )
            Checkbox(taskModel.selected, onCheckedChange = {
                tasksViewModel.onItemChecked(taskModel)
            })
        }
    }
}

@Composable
fun FabDialog(tasksViewModel: TasksViewModel, modifier: Modifier) {
    FloatingActionButton(onClick = {
        tasksViewModel.onShowDialog()
    }, modifier = modifier.padding(16.dp)) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add Task",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTask, onValueChange = {
                    myTask = it
                }, placeholder = {
                    Text(text = "Task Description")
                }, modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(8.dp))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                 }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}
