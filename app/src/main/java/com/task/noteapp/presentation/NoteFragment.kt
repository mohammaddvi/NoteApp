package com.task.noteapp.presentation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.task.noteapp.Note
import com.task.noteapp.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NoteFragment : Fragment(R.layout.fragment_note) {
    private val noteViewModel: NoteViewModel by sharedViewModel()
    private lateinit var notePhoto: ImageView
    private var imageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val saveButton = view.findViewById<Button>(R.id.save_button)
        val changeTitleEditText = view.findViewById<EditText>(R.id.change_title)
        val changeDescriptionEditText = view.findViewById<EditText>(R.id.change_description)
        val addPhoto = view.findViewById<Button>(R.id.add_photo)
        notePhoto = view.findViewById(R.id.note_photo)

        noteViewModel.successfulOperation.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            if (it) findNavController().popBackStack()
        }

        noteViewModel.currentUpdatingNote?.let {
            changeTitleEditText.setText(it.title)
            changeDescriptionEditText.setText(it.description)
            notePhoto.setImageURI(it.imageURl)
        }

        addPhoto.setOnClickListener {
            handlePermission()
        }

        saveButton.setOnClickListener {
            noteViewModel.saveButtonClicked(
                Note(
                    title = changeTitleEditText.text.toString(),
                    description = changeDescriptionEditText.text.toString(),
                    imageURl = imageUri
                )
            )
        }
    }

    private fun handlePermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> openPhotos()
            else -> {
                requestPermission.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) openPhotos()
        }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) openPhotos()
                else Toast.makeText(requireContext(), "we need this permission", Toast.LENGTH_SHORT)
                    .show()
                return
            }
        }
    }

    private fun openPhotos() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1001
        const val PICK_IMAGE_REQUEST = 1002
    }
}