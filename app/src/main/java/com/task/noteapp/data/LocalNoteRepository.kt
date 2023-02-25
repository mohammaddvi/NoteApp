package com.task.noteapp.data

import com.task.noteapp.data.db.DocumentDao
import com.task.noteapp.Note
import com.task.noteapp.repository.NoteRepository

class LocalNoteRepository(private val documentDao: DocumentDao) : NoteRepository {
    override fun deleteNoteById(id: Int): Boolean = documentDao.deleteDocumentById(id)
    override fun addNote(note: Note): Boolean = documentDao.insertDocument(note.toDocument())
    override fun editNote(note: Note): Boolean = documentDao.updateDocument(note.toDocument())
    override fun getAllNotes(): List<Note> = documentDao.getAllDocuments().map { it.toNote() }
    override fun deleteNote(note: Note): Boolean = documentDao.deleteDocument(note.toDocument())
}