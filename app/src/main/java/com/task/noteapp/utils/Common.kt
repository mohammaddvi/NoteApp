package com.task.noteapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.task.noteapp.presentation.NoteFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

fun epochToDateFormat(currentEpoch: Long): String =
    // Create a date formatter with the desired format
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(currentEpoch))

fun <R, T> Flow<List<R>>.flowList(mapper: (R) -> T): Flow<List<T>> =
    this.map { list -> list.map { mapper(it) } }