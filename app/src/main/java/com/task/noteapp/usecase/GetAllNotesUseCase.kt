package com.task.noteapp.usecase

import com.task.noteapp.Note
import com.task.noteapp.repository.NoteRepository

class GetAllNotesUseCase(private val repository: NoteRepository) {
    fun execute():List<Note> = repository.getAllNotes()
}