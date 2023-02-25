package com.task.noteapp.data

import com.task.noteapp.Note
import com.task.noteapp.data.db.Document

fun Note.toDocument(): Document = Document(
    title = title,
    description = description,
    imageUrl = imageURl,
    isEdited = isEdited,
    creationDate = date
)

fun Document.toNote() : Note = Note(title,description,creationDate,isEdited,imageUrl)