package com.task.noteapp.repository

import com.task.noteapp.Note

interface NoteRepository {
    fun addNote(note: Note): Boolean
    fun deleteNote(note: Note): Boolean
    fun deleteNoteById(id: Int): Boolean
    fun editNote(note: Note): Boolean
    fun getAllNotes(): List<Note>
}
