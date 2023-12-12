plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("app.cash.sqldelight") version "2.0.1"
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
//    id("io.ktor.plugin") version "2.3.7"
}

android {
    namespace = "com.example.inputquizapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.inputquizapp"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // SQL Delight
    implementation("app.cash.sqldelight:android-driver:2.0.1")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")

    // Compose ViewModels
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

    //Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

    // TensorFlow libraries
    implementation("org.tensorflow:tensorflow-lite:2.8.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.3.1")

    // KTOR
//    implementation("io.ktor:ktor-server-core")
//    implementation("io.ktor:ktor-server-netty")

    // Compose Navigation
//    implementation("androidx.navigation:navigation-compose:2.6.2")

}

sqldelight {
    databases {
        create("NihongoDatabase") {
            packageName.set("com.talisol.nihongodrill")
        }
    }
}