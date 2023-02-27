package com.task.noteapp.usecase

import com.task.noteapp.Note
import com.task.noteapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNoteUseCase(private val repository: NoteRepository) {
     suspend fun execute(note: Note): Boolean = repository.deleteNote(note) > 0
}