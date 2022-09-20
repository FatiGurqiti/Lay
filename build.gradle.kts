// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.2.2")
        classpath ("com.google.gms:google-services:4.3.13")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }

}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

extra.apply {
    set("appCompatVersion", "1.2.0")
    set("constraintLayoutVersion", "2.0.2")
    set("coreTestingVersion", "2.1.0")
    set("materialVersion", "1.2.1")
    set("roomVersion", "2.4.0")
// testing

    set("junitVersion", "5.7.1")
    set("espressoVersion", "3.1.0")
    set("androidxJunitVersion", "1.1.2")
}

