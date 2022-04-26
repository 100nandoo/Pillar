plugins {
    kotlin("android")
    kotlin("kapt")

    id("com.android.library")
    id("base-android-plugin")
}

android {
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
    implementation("org.jsoup:jsoup:1.14.3")

    implementation(libs.hilt.android)
    implementation(libs.hilt.nav.compose)

    kapt(libs.hilt.compiler)
}
