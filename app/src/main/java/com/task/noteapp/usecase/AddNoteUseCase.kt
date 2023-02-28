package com.task.noteapp.usecase

import com.task.noteapp.Note
import com.task.noteapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend fun execute(note: Note): Boolean {
        return if (note.title.isNotBlank() && note.description.isNotBlank()) {
            repository.addNote(note)
            true
        } else false
    }
}