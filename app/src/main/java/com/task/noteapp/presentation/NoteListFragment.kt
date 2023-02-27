package com.task.noteapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    private val noteViewModel: NoteViewModel by sharedViewModel ()
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var saveButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.note_recycler_view)
        saveButton = view.findViewById(R.id.save)
        saveButton.setOnClickListener {
            findNavController().navigate(R.id.noteFragment)
        }

        noteViewModel.notes.observe(viewLifecycleOwner) {
            notesAdapter = NotesAdapter(it) { note ->
                noteViewModel.editButtonClicked(note)
                findNavController().navigate(R.id.noteFragment)
            }
            recyclerView.adapter = notesAdapter
            notesAdapter.notifyDataSetChanged()
        }
    }
}