package com.task.noteapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.task.noteapp.data.LocalNoteRepository
import com.task.noteapp.data.db.DocumentDao
import com.task.noteapp.data.db.NoteDatabase
import com.task.noteapp.presentation.NoteViewModel
import com.task.noteapp.repository.NoteRepository
import com.task.noteapp.usecase.AddNoteUseCase
import com.task.noteapp.usecase.DeleteNoteUseCase
import com.task.noteapp.usecase.EditNoteUseCase
import com.task.noteapp.usecase.GetAllNotesUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noteDB = module {
    fun provideDataBase(context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "note_db")
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(dataBase: NoteDatabase): DocumentDao = dataBase.documentDao()

    single { provideDataBase(androidContext()) }
    factory { provideDao(get()) }
}


val noteModule = module {
    factory<NoteRepository> { LocalNoteRepository(get()) }
    factory { AddNoteUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    factory { EditNoteUseCase(get()) }
    factory { GetAllNotesUseCase(get()) }
    viewModel { NoteViewModel(get(), get(), get(), get()) }
}