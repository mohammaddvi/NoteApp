package com.task.noteapp.usecase

import com.task.noteapp.Note
import com.task.noteapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class EditNoteUseCase(private val repository: NoteRepository) {
    suspend fun execute(note: Note): Boolean {
        return if (note.title.isNotBlank() && note.description.isNotBlank())
            repository.editNote(note) > 0 else false
    }
}