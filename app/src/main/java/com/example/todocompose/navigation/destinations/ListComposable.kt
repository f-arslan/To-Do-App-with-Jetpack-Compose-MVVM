package com.example.todocompose.navigation.destinations

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todocompose.ui.screens.list.ListScreen
import com.example.todocompose.ui.viewmodels.SharedViewModel
import com.example.todocompose.util.Constants.LIST_ARGUMENT_KEY
import com.example.todocompose.util.Constants.LIST_SCREEN
import com.example.todocompose.util.toAction

@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Extension function
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        Log.d("ListComposable", action.name) // You can observe.

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }

       ListScreen(navigateToTaskScreen = navigateToTaskScreen, sharedViewModel = sharedViewModel)

    }
}