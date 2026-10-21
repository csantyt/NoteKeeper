pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NoteKeper"
include(":app")

// OneDrive bloquea app/build y Gradle falla con AccessDeniedException.
// La compilación queda en AppData, fuera de la carpeta sincronizada.
gradle.beforeProject {
    val localAppData = System.getenv("LOCALAPPDATA") ?: System.getProperty("java.io.tmpdir")
    layout.buildDirectory.set(java.io.File(localAppData, "NoteKeper-build/$name"))
}
 