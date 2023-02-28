package com.task.noteapp.repository

import com.task.noteapp.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note): Int
    suspend fun editNote(note: Note): Int
    fun getNoteList(): Flow<List<Note>>
}
