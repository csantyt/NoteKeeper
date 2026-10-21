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

rootProject.name = "NoteKeeper"
include(":app")

// OneDrive a veces bloquea app/build y Gradle falla con AccessDeniedException.
// Si eso pasa, activa el ajuste en ~/.gradle/gradle.properties con:
// notekeeper.buildOutsideOneDrive=true
// La compilación pasa a LOCALAPPDATA, fuera de la carpeta sincronizada.
if (providers.gradleProperty("notekeeper.buildOutsideOneDrive").orNull == "true") {
    gradle.beforeProject {
        val localAppData = System.getenv("LOCALAPPDATA") ?: System.getProperty("java.io.tmpdir")
        layout.buildDirectory.set(java.io.File(localAppData, "NoteKeeper-build/$name"))
    }
}
 