package com.task.noteapp.data

import android.net.Uri
import com.task.noteapp.Note
import com.task.noteapp.data.db.Document
import com.task.noteapp.utils.epochToDateFormat

fun Note.toDocument(): Document = Document(
    id = id,
    title = title,
    description = description,
    imageUrl = imageURl.toString(),
    isEdited = isEdited,
)

fun Document.toNote(): Note =
    Note(
        id,
        title,
        description,
        epochToDateFormat(creationDate),
        isEdited,
        imageUrl?.let { Uri.parse(it) }
    )