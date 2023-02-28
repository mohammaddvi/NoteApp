package com.task.noteapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.task.noteapp.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    private val noteViewModel: NoteViewModel by sharedViewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var addNoteImageButton: ImageButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.note_recycler_view)
        addNoteImageButton = view.findViewById(R.id.save)

        noteViewModel.successfulOperation.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }

        addNoteImageButton.setOnClickListener {
            findNavController().navigate(R.id.noteFragment)
        }

        noteViewModel.notes.observe(viewLifecycleOwner) {
            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.layoutManager = layoutManager
            val notesAdapter = NotesAdapter(it, onRemoveNoteClicked = { currentNote ->
                noteViewModel.deleteButtonClicked(currentNote)
            }, onEditNoteClicked = { currentNote ->
                val action =
                    NoteListFragmentDirections.actionListToAddNote().setEditNote(currentNote)
                findNavController().navigate(action)
            })
            recyclerView.adapter = notesAdapter
            notesAdapter.notifyDataSetChanged()
        }
    }
}