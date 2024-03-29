plugins {
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
    maven(url = "https://plugins.gradle.org/m2/")
}

dependencies {
    implementation(libs.kgp)
    implementation(libs.agp)
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.dokka.core)
    implementation(libs.gms)
    implementation(libs.firebase.crashlytics.plugin)
    implementation(libs.firebase.performance.plugin)
    implementation(libs.hilt.gradle.plugin)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
