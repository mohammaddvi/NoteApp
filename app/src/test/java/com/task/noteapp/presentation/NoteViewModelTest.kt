package com.task.noteapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.noteapp.usecase.AddNoteUseCase
import com.task.noteapp.usecase.DeleteNoteUseCase
import com.task.noteapp.usecase.EditNoteUseCase
import com.task.noteapp.usecase.GetAllNotesUseCase
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.task.noteapp.Note
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

class NoteViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getAllNotesUseCase: GetAllNotesUseCase

    @RelaxedMockK
    private lateinit var addNoteUseCase: AddNoteUseCase

    @RelaxedMockK
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @RelaxedMockK
    private lateinit var editNoteUseCase: EditNoteUseCase
    private lateinit var viewModel: NoteViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    private fun createViewModel() =
        NoteViewModel(addNoteUseCase, getAllNotesUseCase, deleteNoteUseCase, editNoteUseCase)

    @Test
    fun `when user delete a note, make sure user case call`() = runBlocking {
        val slot = slot<Note>()
        coEvery { deleteNoteUseCase.execute(any()) }.coAnswers { true }
        val viewModel = createViewModel()
        viewModel.deleteButtonClicked(sampleNote)
        coVerify(exactly = 1) {
            deleteNoteUseCase.execute(capture(slot))
        }
        assertEquals(sampleNote, slot.captured)
    }

    @Test
    fun `when user delete a note, should update status live data`() = runBlocking {
        val status = true
        coEvery { deleteNoteUseCase.execute(any()) }.coAnswers { status }
        val viewModel = createViewModel()
        viewModel.deleteButtonClicked(sampleNote)
        delay(100)
        assertEquals(status, viewModel.successfulOperation.value)
    }

    @Test
    fun `when user add a note, make sure usecase has been executed`() = runBlocking {
        val slot = slot<Note>()
        coEvery { addNoteUseCase.execute(any()) }.coAnswers { true }
        val viewModel = createViewModel()
        viewModel.saveButtonClicked(sampleNote)
        coVerify(exactly = 1) {
            addNoteUseCase.execute(capture(slot))
        }
        assertEquals(sampleNote, slot.captured)
    }

    @Test
    fun `when user edit a note, make sure usecase has been executed`() = runBlocking {
        val slot = slot<Note>()
        coEvery { editNoteUseCase.execute(any()) }.coAnswers { true }
        val viewModel = createViewModel()
        viewModel.editButtonClicked(sampleNote, sampleNote2)
        coVerify(exactly = 1) {
            editNoteUseCase.execute(capture(slot))
        }
        assertEquals(sampleNote2, slot.captured)
    }


    val sampleNote = Note(title = "hi", description = "this is test")
    val sampleNote2 = Note(title = "hi2", description = "this is test2", isEdited = true)
}