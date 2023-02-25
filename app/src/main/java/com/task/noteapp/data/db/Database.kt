package com.task.noteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Document::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun documentDao(): DocumentDao

    companion object {
        private val INSTANCE: Database? = null
    }
}