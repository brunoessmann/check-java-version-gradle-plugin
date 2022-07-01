# Check Java Version Gradle Plugin

The `check-java-version` Gradle plugin is a settings plugin that ensures that
the Java version used to execute your Gradle build is in a certain range. 

Ever wondered why your build fails only to find an inconspicuous 
`unsupported class file version` buried in pages of build output?
Often it is not even your own project, but a third party library that has a
dependency on a minimum Java version.

Sounds familiar? Then this simple plugin might be for you. The sole purpose 
of this plugin is to abort the build with a big friendly and easy to spot
message if you are not using the intended Java version:

```
         )))      *****************************        )))
        (((      *     INVALID JAVA VERSION    *      (((
      +-----+    *  BUILD REQUIRES JAVA >= 17  *    +-----+
     [|     |    *     ACTIVE VERSION IS 8     *    |     |]
      `-----'     *****************************     `-----'

FAILURE: Build failed with an exception.

* What went wrong:
Build requires Java 17, active version is 8
```

That's all that this plugin does, nothing more, nothing less.

At first glance this is not very useful, but whenever a new team member cannot
build a project because of a legacy Java version it might come in handy. It 
doesn't do anything you wouldn't find out anyway, but it makes it more obvious.

So, if this plugin at some point saves you a couple of minutes, then the 
inclusion in your build has already been worthwhile.

## Usage

To use this plugin simply include it in the plugins block of your settings
and configure the Java version you need in the `checkJavaVersion` block.

Please note, this is a _settings plugin_ and not a project plugin, therefore 
the declaration is done in the _settings_ file.

**settings.gradle.kts** (Kotlin)
```kotlin
plugins {
    id("ch.essmann.gradle.check-java-version") version "1"
}

checkJavaVersion {
	minimumJavaVersion.set(JavaLanguageVersion.of(8)) // optional
	maximumJavaVersion.set(JavaLanguageVersion.of(11)) // optional
}
```

**settings.gradle** (Groovy)
```groovy
plugins {
    id 'ch.essmann.gradle.check-java-version' version '1'
}

checkJavaVersion {
	minimumJavaVersion = JavaLanguageVersion.of('8') // optional
	maximumJavaVersion = JavaLanguageVersion.of('11') // optional
}
```

## Configuration

Configuration of the plugin is done in the `checkJavaVersion` block in the
settings file (yes, this block is also in the settings file).

You can specify either `minimumJavaVersion` or `maximumJavaVersion` as needed.
Both of optional, but if you omit both the plugin obviously won't do anything
because there is no default value for either of them.

```
minimumJavaVersion <= activeJavaVersion <= maximumJavaVersion
```

To pin the project to a specific major Java version use the same version
for `minimumJavaVersion` or `maximumJavaVersion`.

The Java version has to be specified as 
[Java Toolchain support](https://docs.gradle.org/current/userguide/toolchains.html)
[language version](https://docs.gradle.org/current/javadoc/org/gradle/jvm/toolchain/JavaLanguageVersion.html),
e.g. [`JavaLanguageVersion.of(11)`](https://docs.gradle.org/current/javadoc/org/gradle/jvm/toolchain/JavaLanguageVersion.html).

## Java Toolchain Support

Please note that this plugin does not have any relation to the Java toolchain
support version that you can define for your project.

This plugin merely makes sure that your Gradle instance is running the Java
version that you would expect it to run with.

Also, unless you really want to make sure that a specific Java version is used 
I would suggest to only use `minimumJavaVersion` and then set the Java 
toolchain support language version as desired. Higher Java versions are in
most circumstances perfectly capable of compiling compatible versions for 
lower Java versions.

References:
- [Gradle Java extension and java toolchain languageVersion](https://docs.gradle.org/current/userguide/java_plugin.html#sec:java-extension)
- [Toolchain support for JVM projects](https://docs.gradle.org/current/userguide/toolchains.html)
- [Kotlin Gradle Java toolchain support](https://kotlinlang.org/docs/gradle.html#gradle-java-toolchains-support)

## Plugin Development

Some notes about maintaining this plugin itself.

- At the time of this writing (Gradle 7.5-rc-2) does not support extensions
  in the settings file automatically. There is a "helper function" defined in
  `org/gradle/kotlin/dsl/CheckJavaVersion.kt` that enables it.
- To deploy to a local maven repository for testing add the following block
  at the top of your settings file:
```
pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenLocal()
  }
}
```
- Then build and deploy locally using `./gradlew clean build publishToMavenLocal`
- To release a new version adjust `version` in the `build.gradle.kts`,
  commit the changes, add a tag for the version and push it to the repo.
  Then build and release it.
