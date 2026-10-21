<p align="center">
  <img src="docs/logo.png" alt="Logo de NoteKeeper" width="323">
</p>

<h1 align="center">NoteKeeper</h1>

<p align="center">Tus ideas, siempre a mano.</p>

## Descripción

NoteKeeper es una app de notas para Android, escrita en Kotlin. Funciona sin internet: los datos se guardan en el teléfono con Room. Cada cuenta solo ve sus propias notas.

## Funciones

- Pantalla de inicio (splash) con el logo, el nombre y el lema.
- Registro e inicio de sesión locales, sin servidor.
- Sesión persistente en el dispositivo. Si sigue siendo válida, el splash abre directo las notas.
- Crear, editar y eliminar notas. Cada nota tiene título, texto y fecha.
- Búsqueda en la lista de notas, por título o por texto.
- Cerrar sesión desde el menú de las notas.

## Tecnologías

Versiones tomadas de `gradle/libs.versions.toml`, `app/build.gradle.kts` y `gradle/wrapper/gradle-wrapper.properties`.

| Tecnología | Versión |
| --- | --- |
| Kotlin | 1.9.24 |
| Vistas XML y ViewBinding | ViewBinding activado |
| Material 3 | 1.12.0 |
| MVVM con ViewModel, LiveData y Flow | Lifecycle 2.8.7 |
| Room | 2.6.1 |
| minSdk | 26 |
| targetSdk | 35 |
| Gradle Kotlin DSL | Gradle 8.10.2, Android Gradle Plugin 8.8.0 |

## Estructura del proyecto

```
app/src/main/java/com/csantyt/notekeeper/
├── data/
│   ├── AppDatabase.kt
│   ├── NoteDao.kt
│   ├── Session.kt
│   └── UserDao.kt
├── model/
│   ├── Note.kt
│   └── User.kt
├── ui/
│   ├── login/
│   │   ├── LoginActivity.kt
│   │   ├── RegisterActivity.kt
│   │   └── UserAccountAdapter.kt
│   ├── notes/
│   │   ├── NoteAdapter.kt
│   │   ├── NoteEditorActivity.kt
│   │   └── NotesActivity.kt
│   └── splash/
│       └── SplashActivity.kt
├── util/
│   ├── AuthValidator.kt
│   ├── NoteSearch.kt
│   ├── PasswordHasher.kt
│   └── UserAvatars.kt
└── viewmodel/
    ├── AuthResult.kt
    ├── LoginViewModel.kt
    └── NoteViewModel.kt
```

## Cómo ejecutarla

1. Abre este repositorio en Android Studio.
2. Sincroniza Gradle con **File → Sync Project with Gradle Files**.
3. Ejecuta la app en un emulador o en un celular con Android 8 o superior.
4. En la primera apertura, crea una cuenta. Después ya aparece en el inicio de sesión.

OneDrive a veces bloquea `app/build` y Gradle falla con `AccessDeniedException`. Si eso ocurre, agrega esta línea en `~/.gradle/gradle.properties`:

```properties
notekeeper.buildOutsideOneDrive=true
```

Con esa propiedad, la compilación se escribe en `%LOCALAPPDATA%\NoteKeeper-build`, fuera de la carpeta sincronizada. Sin ella, Gradle compila dentro del proyecto.

## Próximos pasos

- [ ] Contraseñas con sal (PBKDF2)
- [ ] Migraciones de Room
- [ ] Conservar el texto del editor al girar la pantalla
- [x] Pruebas unitarias
- [ ] Agregar capturas

## Bitácora y autor

El detalle del desarrollo está en [BITACORA.md](BITACORA.md).

**Carlos Santiago Torres Castrillón**

- GitHub: [github.com/csantyt](https://github.com/csantyt)
- LinkedIn: [linkedin.com/in/santiago-torres-castrillon](https://www.linkedin.com/in/santiago-torres-castrillon)
