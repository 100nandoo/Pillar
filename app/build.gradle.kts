plugins {
    kotlin("android")
    kotlin("kapt")

    id("com.android.application")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppCoordinates.compileSDK

    defaultConfig {
        minSdk = AppCoordinates.minSDK
        targetSdk = AppCoordinates.targetSDK

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
}

dependencies {
    implementation(project(":ui"))
    implementation(project(":remote"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlinx.coroutines)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ----------------------------- COMPOSE ------------------------------
    // Integration with activities
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.foundation)
    // Compose Material Design
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Integration with ViewModels
    implementation(libs.androidx.compose.viewmodel)
    // UI Tests
    androidTestImplementation(libs.androidx.test.compose.junit)

    // ----------------------------- TEST ------------------------------
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
