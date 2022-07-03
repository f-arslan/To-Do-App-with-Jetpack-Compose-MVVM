package com.example.todocompose.util

/*
    todo: In this class, we will wrap the response of this variable to be a request
     state inside stateFlow (in SharedViewModel.kt) and we will set the requests to loading
     only we get value state the state request, if the state is success
     with that we observe our flows easily.
 */
sealed class RequestState<out T> {
    object Idle: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()
}
