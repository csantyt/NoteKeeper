package com.csantyt.notekeeper

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Comprueba en un dispositivo que el paquete instalado es el de esta app.
 * Las reglas de acceso, búsqueda y hash se prueban en la máquina, sin emulador.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun elPaqueteInstaladoEsNoteKeeper() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.csantyt.notekeeper", appContext.packageName)
    }
}
