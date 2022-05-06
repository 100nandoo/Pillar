import java.io.FileInputStream
import java.util.Properties

plugins {
    kotlin("android")
    kotlin("kapt")

    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    compileSdk = AppCoordinates.compileSDK

    signingConfigs {
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        val properties = Properties()
        properties.load(FileInputStream(keystorePropertiesFile))
        create("release") {
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
            storeFile = rootProject.file(properties.getProperty("storeFile"))
            storePassword = properties.getProperty("storePassword")
        }
    }

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
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":ui"))
    implementation(project(":remote"))
    implementation(libs.kotlinx.coroutines)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation)

    // ----------------------------- AndroidX ------------------------------
    implementation(libs.androidx.viewmodel.ktx)

    implementation(libs.hilt.android)
    implementation(libs.hilt.nav.compose)

    kapt(libs.hilt.compiler)
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")

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

    // ----------------------------- Other Libs ------------------------------
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.performance)
    implementation(libs.firebase.inapp.messaging)

    // ----------------------------- TEST ------------------------------
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
