package com.andresp.ontoptest.presentation

import kotlin.random.Random

fun generatePassword(length: Int, uppercase: Boolean, numbers: Boolean, symbols: Boolean): String {
    val letters = ('a'..'z').toList()
    val upperLetters = ('A'..'Z').toList()
    val digits = ('0'..'9').toList()
    val specialChars = listOf('!', '@', '#', '$', '%', '^', '&', '*')

    val availableCharacters = mutableListOf<Char>().apply {
        addAll(letters)
        if (uppercase) addAll(upperLetters)
        if (numbers) addAll(digits)
        if (symbols) addAll(specialChars)
    }

    return buildString {
        repeat(length) {
            append(availableCharacters[Random.nextInt(availableCharacters.size)])
        }
    }
}

fun capitalizePhrase(phrase: String): String {
    val words = phrase.toCharArray()
    val result = StringBuilder()
    var capitalizeNext = true

    for (char in words) {
        if (char.isWhitespace()) {
            capitalizeNext = true
            result.append(char)
        } else if (capitalizeNext) {
            result.append(char.uppercaseChar())
            capitalizeNext = false
        } else {
            result.append(char)
        }
    }

    return result.toString()
}
