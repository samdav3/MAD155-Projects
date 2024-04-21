// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion = "1.6.21"

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath(kotlin("gradle-plugin", version = "1.9.21"))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath ("com.google.gms:google-services:4.4.1")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
        gradlePluginPortal()
    }
}


plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    kotlin("jvm") version "1.9.21" apply false
    //id("com.android.application") version "8.1.0" apply false
    //id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.7.4" apply false
}
