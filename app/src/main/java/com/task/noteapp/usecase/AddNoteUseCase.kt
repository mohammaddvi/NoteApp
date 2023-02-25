package com.task.noteapp.usecase

import com.task.noteapp.Note
import com.task.noteapp.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    fun execute(note: Note):Boolean = repository.addNote(note)
}