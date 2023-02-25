package com.task.noteapp

import android.app.Application
import com.task.noteapp.di.noteDB
import com.task.noteapp.di.noteModule
import org.koin.core.context.startKoin

class NoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(listOf(noteDB, noteModule)) }
    }
}