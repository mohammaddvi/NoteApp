package com.task.noteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Document::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
}