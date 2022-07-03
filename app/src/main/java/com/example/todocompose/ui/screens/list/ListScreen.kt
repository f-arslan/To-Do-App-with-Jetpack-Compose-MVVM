package com.example.todocompose.ui.screens.list

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todocompose.R
import com.example.todocompose.ui.theme.fabAppBarBackgroundColor
import com.example.todocompose.ui.viewmodels.SharedViewModel
import com.example.todocompose.util.SearchAppBarState

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
    }

    // todo: Collects values from state flow update if there is change
    // todo: if we use by state, it returns the list
    // todo: that's mean we don't need to use .value function
    val allTasks by sharedViewModel.allTasks.collectAsState()

    // for (task in allTasks.value) {
        // todo: if we don't have any task, this place not gonna trigger.
        // todo: after we added item to database, that will automatically triggered
        // Log.d("ListScreen", "Task: ${task.title}")
    // }

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    Scaffold(
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
                  ListContent(
                      tasks = allTasks,
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

