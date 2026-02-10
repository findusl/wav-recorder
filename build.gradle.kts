plugins {
	alias(libs.plugins.android.kotlin.multiplatform.library) apply false
	alias(libs.plugins.kotlinMultiplatform) apply false
	alias(libs.plugins.vanniktech.mavenPublish) apply false
	alias(libs.plugins.ktlint)
}

val printKtlintFormatTask = tasks.register("printKtlintFormatTask") {
	doLast {
		println("Use ./gradlew ktlintFormat to fix formatting issues")
	}
}

tasks
	.matching { it.name.startsWith("ktlint") && it.name.endsWith("Check") }
	.configureEach { finalizedBy(printKtlintFormatTask) }

allprojects {
	apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)

	ktlint {
		kotlinScriptAdditionalPaths {
			include(
				fileTree(
					mapOf(
						"dir" to projectDir,
						"include" to listOf("*.gradle.kts", "gradle/**/*.gradle.kts"),
					),
				),
			)
		}
		filter {
			exclude("**/generated/**")
			exclude("**/BuildKonfig.kt")
		}
	}

	tasks
		.matching { it.name.startsWith("ktlint") && it.name.endsWith("Check") }
		.configureEach { finalizedBy(printKtlintFormatTask) }
}
