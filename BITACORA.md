# Bitácora — NoteKeeper

Fecha: 20 de octubre de 2026

## Qué es el proyecto

NoteKeeper es una aplicación de notas para Android, hecha en Kotlin. Guarda los datos en el teléfono con Room, así que no necesita internet. Cada cuenta solo ve sus propias notas.

## Qué hace la app

1. Pantalla de inicio en negro, con el logo dorado, el nombre NoteKeeper y la frase «Tus ideas, siempre a mano». Una barra inferior avanza mientras abre la siguiente pantalla.
2. Si ya hay una sesión válida, entra directo a las notas. Si no, va al inicio de sesión.
3. Registro e inicio de sesión locales. La contraseña se guarda como hash SHA-256, no en texto plano.
4. En el login se listan las cuentas de este dispositivo. Cada una tiene un retrato distinto. Al tocarla, el retrato grande cambia y solo falta la contraseña.
5. Lista de notas del usuario, con búsqueda.
6. Crear, editar y eliminar notas. Cada nota tiene título, texto y fecha.
7. Cerrar sesión desde el menú de las notas.

## Cambios de esta entrega

- Se alineó el paquete del código con `com.csantyt.notekeeper`, para que el proyecto compile.
- La app corre desde Android 8 (`minSdk` 26) hasta el SDK 35.
- Se quitó el usuario fijo y la pantalla extra de bienvenida.
- Las notas se cargan al abrir la lista, no solo después de guardar una nueva.
- El logo se cambió al perfil dorado, y la pantalla de inicio quedó en negro con el nombre y la frase de la marca.
- La compilación se escribe fuera de OneDrive, en `AppData\Local\NoteKeeper-build`. OneDrive bloqueaba `app\build` y Gradle fallaba con `AccessDeniedException`.

## Cambios del 20 de octubre de 2026

- El nombre visible quedó unificado en NoteKeeper. El paquete y el `applicationId` pasaron a `com.csantyt.notekeeper`.
- El logo se exportó con transparencia. El icono del lanzador es adaptativo, con fondo negro y el perfil dorado, y hay un icono de 512×512 para Play Store.
- Se dejó de seguir la carpeta `.idea` y se agregó `.idea/` al `.gitignore`.
- El desvío de la compilación fuera de OneDrive quedó opcional. Se activa con `notekeeper.buildOutsideOneDrive=true` en `~/.gradle/gradle.properties`.

## Comentarios técnicos

- La base se llama `note_keeper_db`. Si cambia el esquema, Room borra y vuelve a crear las tablas (`fallbackToDestructiveMigration`). Por eso, al instalar esta versión hay que crear la cuenta de nuevo.
- La sesión guarda solo el id del usuario en preferencias del teléfono. Al abrir, se comprueba que ese usuario todavía exista.
- Las notas tienen una clave foránea hacia el usuario. Si se borra la cuenta, sus notas se borran con ella.
- Los retratos no se guardan en la base: se asignan según el id de la cuenta, así cada usuario ve siempre la misma imagen.

## Cómo ejecutarla

1. Abrir este repositorio en Android Studio.
2. Sincronizar Gradle con **File → Sync Project with Gradle Files**.
3. Ejecutar en un emulador o en un celular con Android 8 o superior.
4. En la primera apertura, crear una cuenta. Después ya aparece en la lista del login.
