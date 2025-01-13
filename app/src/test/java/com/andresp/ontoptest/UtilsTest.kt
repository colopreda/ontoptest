package com.andresp.ontoptest

import com.andresp.ontoptest.presentation.capitalizePhrase
import com.andresp.ontoptest.presentation.generatePassword
import org.junit.Assert.*
import org.junit.Test

class UtilsTest {

    @Test
    fun testGeneratePasswordLength() {
        val password = generatePassword(length = 10, uppercase = true, numbers = true, symbols = true)
        assertEquals(10, password.length)
    }

    @Test
    fun testCapitalizePhrase() {
        val result = capitalizePhrase("hello world")
        assertEquals("Hello World", result)
    }

    @Test
    fun testCapitalizePhraseWithAllCaps() {
        val result = capitalizePhrase("HELLO WORLD")
        assertEquals("HELLO WORLD", result)
    }

    @Test
    fun testCapitalizePhraseWithMixedCaps() {
        val result = capitalizePhrase("hElLo WoRlD")
        assertEquals("HElLo WoRlD", result)
    }
}
