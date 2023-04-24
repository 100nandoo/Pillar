plugins {
    kotlin("android")
    kotlin("kapt")

    id("com.android.library")
    id("base-android-plugin")
}

android {
    namespace = "org.redaksi.core"
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.jsoup)

    implementation(libs.hilt.android)
    implementation(libs.hilt.nav.compose)

    kapt(libs.hilt.compiler)
}

kapt {
    correctErrorTypes = true
}
