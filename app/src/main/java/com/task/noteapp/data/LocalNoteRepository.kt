package com.task.noteapp.data

import com.task.noteapp.data.db.DocumentDao
import com.task.noteapp.Note
import com.task.noteapp.utils.flowList
import com.task.noteapp.repository.NoteRepository
import kotlinx.coroutines.flow.*

class LocalNoteRepository(private val documentDao: DocumentDao) : NoteRepository {
    override suspend fun deleteNoteById(id: Int): Int = documentDao.deleteDocumentById(id)
    override suspend fun addNote(note: Note) = documentDao.insertDocument(note.toDocument())
    override suspend fun editNote(note: Note): Int = documentDao.updateDocument(note.toDocument())
    override fun getNoteList(): Flow<List<Note>> = documentDao.getAllDocuments().flowList { it.toNote() }
    override suspend fun deleteNote(note: Note): Int = documentDao.deleteDocument(note.toDocument())
}