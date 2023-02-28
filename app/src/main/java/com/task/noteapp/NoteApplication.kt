package com.task.noteapp

import android.app.Application
import com.task.noteapp.di.noteDB
import com.task.noteapp.di.noteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NoteApplication)
            modules(listOf(noteDB, noteModule))
        }
    }
}