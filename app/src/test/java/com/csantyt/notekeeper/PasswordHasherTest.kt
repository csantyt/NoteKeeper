package com.csantyt.notekeeper

import com.csantyt.notekeeper.util.PasswordHasher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * El hash es SHA-256 en hexadecimal, sin sal. Estas pruebas fijan el vector
 * conocido y comprueban que la contraseña no se guarda en texto plano.
 */
class PasswordHasherTest {

    @Test
    fun hashDeAbcCoincideConElVectorSha256() {
        // Vector público de SHA-256 para "abc" en UTF-8.
        assertEquals(
            "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
            PasswordHasher.hash("abc")
        )
    }

    @Test
    fun laMismaContrasenaSiempreDaElMismoHash() {
        assertEquals(PasswordHasher.hash("nota-1234"), PasswordHasher.hash("nota-1234"))
    }

    @Test
    fun otraContrasenaDaOtroHash() {
        assertNotEquals(PasswordHasher.hash("nota-1234"), PasswordHasher.hash("nota-1235"))
    }

    @Test
    fun elResultadoNoEsLaContrasenaOriginal() {
        val raw = "secreta"
        val hash = PasswordHasher.hash(raw)
        assertNotEquals(raw, hash)
        // SHA-256 en hex siempre ocupa 64 caracteres.
        assertEquals(64, hash.length)
    }
}
