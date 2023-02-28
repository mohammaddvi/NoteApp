package com.task.noteapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.Note
import com.task.noteapp.R


class NotesAdapter(
    private val notes: List<Note>,
    private val onRemoveNoteClicked: (Note) -> Unit,
    private val onEditNoteClicked: (Note) -> Unit,
) : ListAdapter<Note, NotesAdapter.ViewHolder>(TaskDiffCallBack()) {

    class TaskDiffCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id;
        }
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = notes[position]
        if (note.isEdited) holder.editedText.visibility = View.VISIBLE else View.GONE
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.description
        holder.dateTextView.text = note.date
        if (note.imageURl.toString() != "null") {
            holder.image.setImageURI(note.imageURl)
            holder.image.visibility = View.VISIBLE
        } else {
            holder.image.visibility = View.GONE
        }

        holder.removeButton.setOnClickListener {
            onRemoveNoteClicked(notes[position])
        }
        holder.editButton.setOnClickListener {
            onEditNoteClicked(notes[position])
        }
        holder.rootConstraint.setOnClickListener {
            holder.editButton.isVisible = !holder.editButton.isVisible
            holder.removeButton.isVisible = !holder.removeButton.isVisible
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = notes.size

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView
        var contentTextView: TextView
        var dateTextView: TextView
        var editedText: TextView
        var image: ImageView
        var removeButton: ImageButton
        var editButton: ImageButton
        var rootConstraint: ConstraintLayout

        init {
            titleTextView = view.findViewById(R.id.title_item)
            contentTextView = view.findViewById(R.id.description_item)
            dateTextView = view.findViewById(R.id.creation_date_item)
            image = view.findViewById(R.id.image_item)
            removeButton = view.findViewById(R.id.remove_button)
            editButton = view.findViewById(R.id.edit_button)
            rootConstraint = view.findViewById(R.id.root)
            editedText = view.findViewById(R.id.edited_text)
        }
    }
}