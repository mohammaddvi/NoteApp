package com.task.noteapp.presentation

import android.net.Uri
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val addNoteUseCase: AddNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase
) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>(emptyList())
    val notes: LiveData<List<Note>> = _notes

    private val _successfulOperation = SingleLiveData<Boolean>()
    val successfulOperation: LiveData<Boolean> = _successfulOperation

    var currentUpdatingNote: Note? = null
        private set


    init {
        getAllNotes()
    }

    private fun saveNote(note: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = addNoteUseCase.execute(note)
                _successfulOperation.postValue(result)
            }
        }
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.execute().collect {
                _notes.postValue(it)
            }
        }
    }

    private fun editNote(note: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                editNoteUseCase.execute(note).also {
                    if (it) currentUpdatingNote = null
                    _successfulOperation.postValue(it)
                }
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteNoteUseCase.execute(note)
            }
        }
    }

    fun saveButtonClicked(newNote: Note) {
        if (currentUpdatingNote == null) saveNote(newNote)
        else {
            editNote(
                currentUpdatingNote!!.copy(
                    title = newNote.title,
                    description = newNote.description,
                    imageURl = newNote.imageURl ?: currentUpdatingNote!!.imageURl,
                    isEdited = true
                )
            )
        }
    }

    fun deleteButtonClicked(note: Note) = deleteNote(note)
    fun editButtonClicked(note: Note) {
        currentUpdatingNote = note
    }

}