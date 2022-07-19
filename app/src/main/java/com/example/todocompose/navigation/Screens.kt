package com.example.todocompose.navigation

import androidx.navigation.NavHostController
import com.example.todocompose.util.Action
import com.example.todocompose.util.Constants.LIST_SCREEN
import com.example.todocompose.util.Constants.SPLASH_SCREEN

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) {
                inclusive = true // that's mean it will remove from stack, if you press back you will not gonna see this.
            }
        }
    }

    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }

    val task: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }


}