enableFeaturePreview("VERSION_CATALOGS")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {

    // TODO CANNOT MOVE IT TO "FAIL_ON_PROJECT_REPOS" because J.S. does not build
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)

    versionCatalogs {
        create("libs") {
            from(files("gradle/wrapper/libs.versions.toml"))
        }
    }

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "Useful Training"
include(":app")