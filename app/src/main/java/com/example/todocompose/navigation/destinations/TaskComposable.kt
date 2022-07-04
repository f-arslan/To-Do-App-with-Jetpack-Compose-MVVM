package com.example.todocompose.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todocompose.ui.screens.task.TaskScreen
import com.example.todocompose.ui.viewmodels.SharedViewModel
import com.example.todocompose.util.Action
import com.example.todocompose.util.Constants.TASK_ARGUMENT_KEY
import com.example.todocompose.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry -> // todo: get from argument listComposable
        // todo: taskId if it comes from floating action button, it return -1
        //  else it returns the taskId from the list item
        //  Using only basic id better than passing whole task object
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId = taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        TaskScreen(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen
        )

    }
}

