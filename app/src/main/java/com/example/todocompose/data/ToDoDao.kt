package com.example.todocompose.data

import androidx.room.*
import com.example.todocompose.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao // Data Access Object
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>> // Asynchronous flow of tasks

    @Query("SELECT * FROM todo_table WHERE id = :taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask> // We don't need to use suspend function, because they are already in the flow

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(toDoTask: ToDoTask) // This function will be run inside coroutine scope

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortedByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortedByHighPriority(): Flow<List<ToDoTask>>
}