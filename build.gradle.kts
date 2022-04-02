import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt

@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versions)
    cleanup
    base
}

allprojects {
    group = PUBLISHING_GROUP
}
val ktlintVersion = libs.versions.ktlint.get()
subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        debug.set(false)
        version.set(ktlintVersion)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
    }
}

task<Exec>("openReport") {
    group = "detekt"
    val reportLocation = rootDir.absolutePath + "/app/build/reports/detekt/detekt.html"

    commandLine("cmd", "/c", "start ${reportLocation.replace("\\", "/")}")
}

task<Exec>("openDetekt") {
    group = "detekt"
    val reportLocation = rootDir.absolutePath + "/app/build/reports/detekt/"
    commandLine("cmd", "/c", "start ${reportLocation.replace("\\", "/")}")
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }

    withType<Detekt>().configureEach {
        // openReport()
        htmlReportFile.isPresent

        reports {
            html {
                required.set(true)
            }
        }
        dependsOn("openReport")
    }
}