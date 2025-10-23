import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "de.findusl"
version = "0.1.1"

kotlin {
    jvm()
    androidLibrary {
        namespace = "de.findusl.wavrecorder"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

		compilerOptions {
			jvmTarget.set(JvmTarget.JVM_11)
		}
    }
	iosX64()
	iosArm64()
	iosSimulatorArm64()
	linuxX64()

    sourceSets {
        commonMain.dependencies {
			implementation(libs.kotlinx.io.core)
			implementation(libs.kotlinx.coroutinesCore)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

		androidMain.dependencies {
			implementation(libs.androidx.annotation)
		}
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "wav-recorder", version.toString())

    pom {
        name = "Wav Recorder"
        description = "A minimalistic kotlin multiplatform wav recording library, intended for use with speech and ai models."
        inceptionYear = "2025"
        url = "https://github.com/findusl/wav-recorder"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "findusl"
                name = "Sebastian"
                url = "https://github.com/findusl"
            }
        }
        scm {
            url = "https://github.com/findusl/wav-recorder"
            connection = "scm:git:https://github.com/findusl/wav-recorder.git"
            developerConnection = "scm:git:ssh://git@github.com/findusl/wav-recorder.git"
        }
    }
}
