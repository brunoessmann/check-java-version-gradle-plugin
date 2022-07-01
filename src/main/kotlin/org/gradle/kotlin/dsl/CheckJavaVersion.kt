package org.gradle.kotlin.dsl

import ch.essmann.gradle.checkjava.CheckJavaVersionExtension
import org.gradle.api.initialization.Settings

@Suppress("UNUSED")
inline fun Settings.checkJavaVersion(configure: CheckJavaVersionExtension.() -> Unit) {
    // This function is needed because Gradle doesn't generate accessors for settings extensions.
    extensions.getByType(CheckJavaVersionExtension::class.java).configure()
}
