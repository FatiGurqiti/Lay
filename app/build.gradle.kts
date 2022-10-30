plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
}

android {
    compileSdk = 32
    buildToolsVersion = "30.0.3"

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dataBinding{
        isEnabled = true
    }

    defaultConfig {
        applicationId = "com.fdev.lay"
        minSdk = 24
        targetSdk = 32
        versionCode = 4
        versionName = "1.4"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
       // compose = true
        viewBinding = true
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
    }
}

dependencies {
    implementation("com.google.firebase:firebase-bom:29.0.0")
    implementation("com.google.firebase:firebase-auth")
    implementation("androidx.core:core:1.7.0")
    implementation("android.arch.lifecycle:extensions:1.1.1")
    implementation("android.arch.lifecycle:viewmodel:1.1.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.annotation:annotation:1.3.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.navigation:navigation-fragment:2.4.1")
    implementation("androidx.navigation:navigation-ui:2.4.1")
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.google.firebase:firebase-storage:20.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.firebase:firebase-perf:20.0.5")
    implementation("com.google.firebase:firebase-firestore-ktx:24.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testCompileOnly("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("androidx.appcompat:appcompat:$rootProject.appCompatVersion")

// Dependencies for working with Architecture components

// Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.5.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.5.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.5.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.appcompat:appcompat-resources:1.4.2")

// Dependencies for working with Architecture components

// Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel:$rootProject.lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata:$rootProject.lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$rootProject.lifecycleVersion")

    //Kotlin
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.0")

    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.cardview:cardview:1.0.0")

}

