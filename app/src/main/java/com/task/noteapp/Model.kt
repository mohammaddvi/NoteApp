package com.task.noteapp

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    val title: String,
    val description: String,
    val date: String?=null,
    val isEdited: Boolean = false,
    val imageURl: Uri? = null
):Parcelable