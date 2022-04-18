package com.task.noteapp.feature.notes.detail.presentation

import android.content.Intent
import com.task.noteapp.BaseUnitTest
import com.task.noteapp.base.NoteAppActivity
import com.task.noteapp.db.entity.NoteEntity
import com.task.noteapp.feature.notes.detail.domain.interactor.DeleteNote
import com.task.noteapp.feature.notes.detail.domain.interactor.SaveNote
import com.task.noteapp.feature.notes.detail.domain.interactor.UpdateNote
import org.junit.Assert
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Spy

class NoteDetailViewModelTest : BaseUnitTest() {

    @InjectMocks
    @Spy
    lateinit var viewModel: NoteDetailViewModel

    @Mock
    lateinit var saveNote: SaveNote

    @Mock
    lateinit var updateNote: UpdateNote

    @Mock
    lateinit var deleteNote: DeleteNote

    @Test
    fun testData() {
        // Given
        val uid = 111
        val title = "title"
        val desc = "desc"
        val image = "www.image.com"
        val createDate = "31/01/2021"
        val isEdited = true
        val data = NoteEntity(uid, title, desc, image, createDate, isEdited)
        val mockIntent = mock(Intent::class.java)
        val mockData = mock(NoteDetailActivity.NavigationData::class.java)

        // When
        `when`(mockIntent.hasExtra(NoteAppActivity.EXTRAS)).thenReturn(true)
        `when`(viewModel.getDataFromIntent(mockIntent)).thenReturn(mockData)
        `when`(mockData.note).thenReturn(data)
        viewModel.parseIntent(mockIntent)

        // Then
        Assert.assertEquals(uid, viewModel.noteEntity?.uid)
        Assert.assertEquals(title, viewModel.titleObservable.get())
        Assert.assertEquals(desc, viewModel.descriptionObservable.get())
        Assert.assertEquals(image, viewModel.imageUrlObservable.get())
        Assert.assertEquals(createDate, viewModel.createdDateObservable.get())
    }
}