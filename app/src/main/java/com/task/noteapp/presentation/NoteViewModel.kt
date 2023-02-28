package com.task.noteapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.noteapp.Note
import com.task.noteapp.usecase.AddNoteUseCase
import com.task.noteapp.usecase.DeleteNoteUseCase
import com.task.noteapp.usecase.EditNoteUseCase
import com.task.noteapp.usecase.GetAllNotesUseCase
import androidx.lifecycle.viewModelScope
import com.task.noteapp.utils.SingleLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NoteViewModel(
    private val addNoteUseCase: AddNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>(emptyList())
    val notes: LiveData<List<Note>> = _notes

    private val _successfulOperation = SingleLiveData<Boolean>()
    val successfulOperation: LiveData<Boolean> = _successfulOperation

    private var userIsEditing = false

    init {
        getAllNotes()
    }

    private fun saveNote(note: Note) {
        viewModelScope.launch {
            val result = addNoteUseCase.execute(note)
            _successfulOperation.postValue(result)
        }
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.execute().collect {
                _notes.postValue(it)
            }
        }
    }

    private fun editNote(oldNote: Note, newNote: Note) {
        viewModelScope.launch {
            editNoteUseCase.execute(
                oldNote.copy(
                    title = newNote.title,
                    description = newNote.description,
                    imageURl = newNote.imageURl ?: oldNote.imageURl,
                    isEdited = true
                )
            ).also {
                if (it) userIsEditing = false
                _successfulOperation.postValue(it)
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            val status = deleteNoteUseCase.execute(note)
            _successfulOperation.postValue(status)
        }
    }

    fun saveButtonClicked(newNote: Note) {
        saveNote(newNote)
    }

    fun deleteButtonClicked(note: Note) = deleteNote(note)
    fun editButtonClicked(oldNote: Note, newNote: Note) = editNote(oldNote, newNote)
}