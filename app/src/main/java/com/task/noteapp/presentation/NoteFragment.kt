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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.noteapp.Note
import com.task.noteapp.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NoteFragment : Fragment(R.layout.fragment_note) {
    private lateinit var notePhoto: ImageView
    private var imageUri: Uri? = null
    private val args: NoteFragmentArgs by navArgs()
    private var userIsEditing: Boolean = false

    private val noteViewModel: NoteViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<ImageButton>(R.id.save_button)
        val changeTitleEditText = view.findViewById<EditText>(R.id.edit_title_item)
        val changeDescriptionEditText = view.findViewById<EditText>(R.id.edit_description_item)
        val addPhoto = view.findViewById<ImageButton>(R.id.add_photo)

        userIsEditing = args.editNote != null
        notePhoto = view.findViewById(R.id.image_item)

        val permissionHandler =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) openPhotos()
            }

        noteViewModel.successfulOperation.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            if (it) findNavController().popBackStack()
        }

        args.editNote?.let {
            changeTitleEditText.setText(it.title)
            changeDescriptionEditText.setText(it.description)
            notePhoto.setImageURI(it.imageURl)
            notePhoto.visibility = View.VISIBLE
        }

        addPhoto.setOnClickListener {
            handlePermission(permissionHandler)
        }

        saveButton.setOnClickListener {
            if (userIsEditing) editNoteTriggered(changeTitleEditText, changeDescriptionEditText)
            else addNoteTriggered(changeTitleEditText, changeDescriptionEditText)
        }
    }

    private fun handlePermission(permissionHandler: ActivityResultLauncher<String>) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.need_permission),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                permissionHandler.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            openPhotos()
        }
    }

    private fun addNoteTriggered(
        changeTitleEditText: EditText,
        changeDescriptionEditText: EditText
    ) {
        noteViewModel.saveButtonClicked(
            Note(
                title = changeTitleEditText.text.toString(),
                description = changeDescriptionEditText.text.toString(),
                imageURl = imageUri
            )
        )
    }

    private fun editNoteTriggered(
        changeTitleEditText: EditText,
        changeDescriptionEditText: EditText
    ) {
        noteViewModel.editButtonClicked(
            args.editNote!!, Note(
                title = changeTitleEditText.text.toString(),
                description = changeDescriptionEditText.text.toString(),
                imageURl = imageUri
            )
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openPhotos()
            }
        }
    }

    private fun openPhotos() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            notePhoto.setImageURI(imageUri)
            notePhoto.visibility = View.VISIBLE
        } else {
            notePhoto.visibility = View.GONE
        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1001
        const val PICK_IMAGE_REQUEST = 1002
    }
}