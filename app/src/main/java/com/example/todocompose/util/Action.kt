package com.example.todocompose.util

enum class Action {
    // This enum class use for task and list controller for handle action
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

fun String?.toAction(): Action {
    return when {
        this == "ADD" -> Action.ADD
        this == "UPDATE" -> Action.UPDATE
        this == "DELETE" -> Action.DELETE
        this == "DELETE_ALL" -> Action.DELETE_ALL
        this == "UNDO" -> Action.UNDO
        else -> return Action.NO_ACTION
    }
}