package com.task.noteapp.presentation

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.Note
import com.task.noteapp.R


class NotesAdapter(private val notes: List<Note>,private val onNoteItemClicked: (Note) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view){
            onNoteItemClicked(notes[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.description
        holder.dateTextView.text = note.date
        holder.image.setImageURI(note.imageURl)
    }

    override fun getItemCount(): Int = notes.size

    inner class ViewHolder(view: View, onNoteItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        var titleTextView: TextView
        var contentTextView: TextView
        var dateTextView: TextView
        var image: ImageView

        init {
            titleTextView = view.findViewById(R.id.title)
            contentTextView = view.findViewById(R.id.description)
            dateTextView = view.findViewById(R.id.creation_date)
            image = view.findViewById(R.id.image)
            view.setOnClickListener {
                onNoteItemClicked(adapterPosition)
            }
        }
    }
}