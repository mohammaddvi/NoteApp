package com.task.noteapp

data class Note(
    val title: String,
    val description: String,
    val date: String,
    val isEdited: Boolean = false,
    val imageURl: String? = null
)