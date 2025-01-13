package com.andresp.ontoptest.presentation.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val isAlive: Boolean,
    val species: String,
    val gender: String,
    val image: String,
)