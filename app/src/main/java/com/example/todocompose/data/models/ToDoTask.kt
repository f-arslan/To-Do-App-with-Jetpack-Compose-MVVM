package com.example.todocompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todocompose.util.Constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE) // It will be used to create a table in the database
data class ToDoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Will automatically be incremented by the database
    val title: String,
    val description: String,
    val priority: Priority
)