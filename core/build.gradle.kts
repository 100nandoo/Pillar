plugins {
    kotlin("android")

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
}
