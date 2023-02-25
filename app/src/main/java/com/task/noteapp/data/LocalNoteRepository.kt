package com.task.noteapp.data

import com.task.noteapp.data.db.DocumentDao
import com.task.noteapp.Note
import com.task.noteapp.repository.NoteRepository

class LocalNoteRepository(private val documentDao: DocumentDao) : NoteRepository {
    override fun addNote(note: Note): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteNote(note: Note): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteNoteById(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun editNote(note: Note): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllNotes(): List<Note> {
        TODO("Not yet implemented")
    }
}