plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.7.0"
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

group = "ch.essmann.gradle"
version = 1

pluginBundle {
    website = "https://git.essmann.ch/gradle/check-java-gradle-plugin"
    vcsUrl = "ssh://git@git.essmann.ch:30001/gradle/check-java-gradle-plugin.git"
    tags = listOf("java", "version")
}

gradlePlugin {
    plugins {
        create("checkjava") {
            id = "ch.essmann.gradle.check-java-version"
            implementationClass = "ch.essmann.gradle.checkjava.CheckJavaVersionPlugin"
            displayName = "Checks that Gradle is running with the correct Java version"
            description = "Checks the Java version before starting the Gradle build"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}
