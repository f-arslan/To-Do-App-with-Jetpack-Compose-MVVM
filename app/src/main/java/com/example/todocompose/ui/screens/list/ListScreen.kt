package com.example.todocompose.ui.screens.list

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todocompose.R
import com.example.todocompose.ui.theme.fabAppBarBackgroundColor
import com.example.todocompose.ui.viewmodels.SharedViewModel
import com.example.todocompose.util.Action
import com.example.todocompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    LaunchedEffect(key1 = true) {
        // Log.d("ListScreen", "LaunchedEffect Triggered")
        // todo: read docs about LaunchedEffect
        sharedViewModel.getAllTasks()
        sharedViewModel.readSortState()
    }

    val action by sharedViewModel.action

    // todo: Collects values from state flow update if there is change
    // todo: if we use by state, it returns the list
    // todo: that's mean we don't need to use .value function
    val allTasks by sharedViewModel.allTasks.collectAsState()

    val searchTasks by sharedViewModel.searchedTasks.collectAsState()

    // for (task in allTasks.value) {
    // todo: if we don't have any task, this place not gonna trigger.
    // todo: after we added item to database, that will automatically triggered
    // Log.d("ListScreen", "Task: ${task.title}")
    // }

    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDataBaseAction = { sharedViewModel.handleDatabaseAction(action = action) },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState, // without not gonna work
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
                  ListContent(
                      allTasks = allTasks,
                      searchedTasks = searchTasks,
                      lowPriorityTasks = lowPriorityTasks,
                      highPriorityTasks = highPriorityTasks,
                      sortState = sortState,
                      searchAppBarState = searchAppBarState,
                      navigateToTaskScreen = navigateToTaskScreen,
                  )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}


@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            // -1 means we are not opening a task, but adding a new one
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabAppBarBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDataBaseAction: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDataBaseAction()
    val scope = rememberCoroutineScope()

    // If there is changes launched effect will be triggered.
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action, taskTitle),
                    actionLabel = setActionLabel(action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Tasks Removed."
        else -> "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}


















