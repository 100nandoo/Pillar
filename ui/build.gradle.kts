plugins {
    kotlin("android")
    kotlin("kapt")

    id("com.android.library")
    id("dagger.hilt.android.plugin")

    id("base-android-plugin")
}

android {
    namespace = "org.redaksi.ui"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":remote"))
    implementation(project(":core"))
    // ----------------------------- AndroidX ------------------------------
    implementation(libs.androidx.viewmodel.ktx)

    implementation(libs.hilt.android)
    implementation(libs.hilt.nav.compose)

    kapt(libs.hilt.compiler)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // ----------------------------- COMPOSE ------------------------------
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.contraint.layout)
    // Compose Material Design
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.viewmodel)

    // Animations
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // ----------------------------- UI ------------------------------
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)
    implementation(libs.accompanist.webview)
    implementation(libs.accompanist.flow.layout)

    implementation(libs.coil)

    implementation(libs.three.ten.abp)
}
