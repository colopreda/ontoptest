package com.andresp.ontoptest.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.andresp.ontoptest.presentation.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedCharacterViewModel @Inject constructor() : ViewModel() {
    private val _selectedCharacter = mutableStateOf<CharacterModel?>(null)
    val selectedCharacter: State<CharacterModel?> = _selectedCharacter

    fun setCharacter(character: CharacterModel) {
        _selectedCharacter.value = character
    }
}