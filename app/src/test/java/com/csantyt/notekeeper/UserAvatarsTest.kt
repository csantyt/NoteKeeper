package com.csantyt.notekeeper

import com.csantyt.notekeeper.R
import com.csantyt.notekeeper.util.UserAvatars
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Hay seis retratos. El id 1 usa el primero y, al pasar del sexto, vuelve a empezar.
 * Un id inválido no se sale del arreglo: cae en el primer retrato.
 */
class UserAvatarsTest {

    @Test
    fun elPrimerUsuarioUsaElPrimerRetrato() {
        assertEquals(R.drawable.avatar_01, UserAvatars.forUser(1))
        assertEquals(R.drawable.avatar_06, UserAvatars.forUser(6))
    }

    @Test
    fun elSeptimoUsuarioRepiteElPrimerRetrato() {
        assertEquals(UserAvatars.forUser(1), UserAvatars.forUser(7))
    }

    @Test
    fun unIdCeroONegativoUsaElPrimerRetrato() {
        assertEquals(R.drawable.avatar_01, UserAvatars.forUser(0))
        assertEquals(R.drawable.avatar_01, UserAvatars.forUser(-2))
    }
}
