package com.csantyt.notekeeper

import com.csantyt.notekeeper.model.Note
import com.csantyt.notekeeper.util.NoteSearch
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * La búsqueda de la lista es local: mira título y texto, sin distinguir mayúsculas.
 * Una consulta en blanco devuelve todas las notas que ya llegaron del usuario.
 */
class NoteSearchTest {

    private val notes = listOf(
        Note(id = 1, userId = 4, title = "Compras", content = "Leche y pan"),
        Note(id = 2, userId = 4, title = "Clase", content = "Repasar Room")
    )

    @Test
    fun consultaVaciaDevuelveTodas() {
        assertEquals(notes, NoteSearch.filter(notes, "   "))
    }

    @Test
    fun encuentraPorTituloOPorTextoSinImportarMayusculas() {
        assertEquals(listOf(notes[0]), NoteSearch.filter(notes, "com"))
        assertEquals(listOf(notes[1]), NoteSearch.filter(notes, "ROOM"))
    }

    @Test
    fun sinCoincidenciaDevuelveListaVacia() {
        assertEquals(emptyList<Note>(), NoteSearch.filter(notes, "viaje"))
    }
}
