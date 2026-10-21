package com.csantyt.notekeeper

import com.csantyt.notekeeper.util.AuthValidator
import com.csantyt.notekeeper.util.PasswordHasher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Reglas de registro e inicio de sesión que usa LoginViewModel.
 * El usuario se recorta; la contraseña no.
 */
class AuthValidatorTest {

    @Test
    fun loginRechazaUsuarioOContrasenaVacios() {
        assertEquals(AuthValidator.LOGIN_EMPTY, AuthValidator.loginError("   ", "clave"))
        assertEquals(AuthValidator.LOGIN_EMPTY, AuthValidator.loginError("ana", ""))
    }

    @Test
    fun loginAceptaElFormatoCuandoAmbosCamposTienenTexto() {
        // Los espacios de la contraseña cuentan: no se recortan.
        assertNull(AuthValidator.loginError("  ana  ", "  "))
    }

    @Test
    fun registroExigeUsuarioDeTresYContrasenaDeCuatro() {
        assertEquals(AuthValidator.USER_SHORT, AuthValidator.registerError("  ab ", "clave", "clave", false))
        assertEquals(AuthValidator.PASSWORD_SHORT, AuthValidator.registerError("ana", "123", "123", false))
    }

    @Test
    fun registroRevisaLaLongitudAntesDeLaConfirmacion() {
        // Una contraseña corta no llega a compararse con la confirmación.
        assertEquals(AuthValidator.PASSWORD_SHORT, AuthValidator.registerError("ana", "12", "otra", false))
        assertEquals(AuthValidator.PASSWORD_MISMATCH, AuthValidator.registerError("ana", "clave", "claves", false))
    }

    @Test
    fun registroSoloAvisaUsuarioTomadoSiElFormatoYaEsValido() {
        assertEquals(AuthValidator.USER_TAKEN, AuthValidator.registerError("ana", "clave", "clave", true))
        assertEquals(AuthValidator.USER_SHORT, AuthValidator.registerError("ab", "clave", "clave", true))
    }

    @Test
    fun laContrasenaCoincideSoloConSuHash() {
        val stored = PasswordHasher.hash("clave")
        assertTrue(AuthValidator.passwordMatches(stored, "clave"))
        assertFalse(AuthValidator.passwordMatches(stored, "Clave"))
        // Guardar el texto plano no debe pasar como contraseña correcta.
        assertFalse(AuthValidator.passwordMatches("clave", "clave"))
    }
}
