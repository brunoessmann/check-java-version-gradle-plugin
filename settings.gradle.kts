plugins {
    id("ch.essmann.gradle.check-java-version") version "1"
}

checkJavaVersion {
    minimumJavaVersion.set(JavaLanguageVersion.of(8))
}

rootProject.name = "check-java-version"
