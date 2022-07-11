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
        languageVersion.set(JavaLanguageVersion.of(8))
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
    website = "https://github.com/brunoessmann/check-java-version-gradle-plugin"
    vcsUrl = "https://github.com/brunoessmann/check-java-version-gradle-plugin.git"
    tags = listOf("java", "check", "version")
}

gradlePlugin {
    plugins {
        create("checkjava") {
            id = "ch.essmann.gradle.check-java-version"
            implementationClass = "ch.essmann.gradle.checkjava.CheckJavaVersionPlugin"
            displayName = "A plugin that ensures that the Java version used to execute your Gradle build is in a certain range."
            description = """
                This plugin checks the Java version your Gradle build is running with.
                
                If the version is lower than the required minimum version or it exceeds the maximum version accepted, 
                then the build is aborted with a friendly, easy to spot error message pointing towards the problem.
                
                This should help to avoid having to dig through the build output to find only to find an unsupported class 
                file version error messages buried somehere in the output due to a bad Java runtime version.
            """.trimIndent()
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
