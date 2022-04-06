import org.gradle.api.JavaVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = AppCoordinates.compileSDK

    defaultConfig {
        minSdk = AppCoordinates.minSDK
        targetSdk = AppCoordinates.targetSDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
