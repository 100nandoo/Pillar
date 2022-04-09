plugins {
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.6.10"

    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("base-android-plugin")
}

android {
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(libs.kotlinx.json)
    api(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.androidx.annotation)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
}

kapt {
    correctErrorTypes = true
}