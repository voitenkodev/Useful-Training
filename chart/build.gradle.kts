@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.serialization)
}

kotlin {
    android()
    iosX64("uikitX64")
    iosArm64("uikitArm64")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.logger)

                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }

        val androidMain by getting {}
        val uikitMain by creating { dependsOn(commonMain) }
    }

    kotlin.sourceSets.all {
        languageSettings.optIn("androidx.compose.ui.text.ExperimentalTextApi")
        languageSettings.optIn("androidx.compose.foundation.ExperimentalFoundationApi")
        languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")
    }
}

android {
    namespace = "dev.voitenko.usefultraining.chart"
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig { minSdk = 21 }
    buildTypes.getByName("release") { isMinifyEnabled = true }
}