package com.task.noteapp.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDocument(document: Document)

    @Query("SELECT * FROM documents")
    fun getAllDocuments(): Flow<List<Document>>

    @Query("DELETE FROM documents WHERE id =:id")
    fun deleteDocumentById(id: Int): Boolean

    @Delete
    fun deleteDocument(document: Document): Boolean

    @Update
    fun updateDocument(document: Document): Boolean

}