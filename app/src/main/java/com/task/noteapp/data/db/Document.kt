package com.task.noteapp.data.db

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "documents")
data class Document(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "description") val description: String,

    @ColumnInfo(name = "image_url") val imageUrl: String? = null,

    @ColumnInfo(name = "isEdited") val isEdited: Boolean = false,
) {
    @ColumnInfo(name = "creation_date")
    @Ignore
    val creationDate: Long = System.currentTimeMillis()
}
