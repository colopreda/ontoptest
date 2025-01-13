package com.andresp.ontoptest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.andresp.ontoptest.data.ApiRepository
import com.andresp.ontoptest.presentation.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    repository: ApiRepository
) : ViewModel() {
    var characters: Flow<PagingData<CharacterModel>> = repository.getAllCharacters()
}