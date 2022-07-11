# Plugin Development

This document contains some notes about maintaining the plugin itself.

## Gradle Settings Extension

At the time of this writing (Gradle 7.5-rc-2) does not support extensions
in the settings file automatically. There is a "helper function" defined in
`org/gradle/kotlin/dsl/CheckJavaVersion.kt` that enables it.

## Local Deployment

To deploy to a local maven repository for testing add the following block
at the top of your settings file:
```
pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenLocal()
  }
}
```
Then build and deploy locally using `./gradlew clean build publishToMavenLocal`

## Releasing a New Version

1. To release a new version adjust the `version` field in the `build.gradle.kts`.
2. Commit the changes and push to GitHub.
3. Tag the repo for the version with `v<VERSION>` and push it to the repo.
4. Publish the plugin to the [Gradle Plugin Portal](https://plugins.gradle.org/) 
   using `./gradlew publishPlugins`.
5. Add a new version on GitHub.
