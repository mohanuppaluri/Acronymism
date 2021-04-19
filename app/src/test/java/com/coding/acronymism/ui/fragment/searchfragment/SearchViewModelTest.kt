package com.coding.acronymism.ui.fragment.searchfragment

import com.coding.acronymism.room.entity.Acronym
import com.coding.acronymism.room.repository.AcronymRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchViewModelTest {
    @Mock
    private lateinit var acronymRepository: AcronymRepository
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var acronym: Acronym

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        acronym = Acronym(id = 1, name = "USA")
        searchViewModel = SearchViewModel(acronymRepository)
    }

    @Test
    fun `addAcronym SHOULD call addAcronym function in acronymRepository`() {
        // GIVEN

        // WHEN
        searchViewModel.addAcronym(acronym)

        // THEN
        verify(acronymRepository).addAcronym(acronym)
    }

    @Test
    fun `deleteAcronym SHOULD call deleteAcronym function in acronymRepository`() {
        // GIVEN

        // WHEN
        searchViewModel.deleteAcronym(acronym)

        // THEN
        verify(acronymRepository).deleteAcronym(acronym)
    }

}