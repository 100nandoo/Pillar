plugins {
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.9.20"

    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("base-android-plugin")
}

android {
    namespace = "org.redaksi.data.remote"
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
    testImplementation(libs.coroutine.test)
}

kapt {
    correctErrorTypes = true
}
