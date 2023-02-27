package com.task.noteapp.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: Document)

    @Query("SELECT * FROM documents")
    fun getAllDocuments(): Flow<List<Document>>

    @Query("DELETE FROM documents WHERE id =:id")
    suspend fun deleteDocumentById(id: Int): Int

    //this return type indicates the number of rows were affected by query so if it's bigger than 0 it's successful
    @Delete
    suspend fun deleteDocument(document: Document): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDocument(document: Document): Int

}