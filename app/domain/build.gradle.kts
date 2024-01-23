plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = ProjectSettings.namespace("domain")
    compileSdk = ProjectSettings.compileSdk

    defaultConfig {
        minSdk = ProjectSettings.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = ProjectSettings.Versions.jvmTarget
    }
}

dependencies {
    implementation(libs.core.ktx)
    testImplementation(libs.junit)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.coroutines)

    implementation(project(ProjectSettings.Modules.CoreCommon.path))
    implementation(project(ProjectSettings.Modules.CoreDataAccessDataStore.path))
    implementation(project(ProjectSettings.Modules.CoreApi.path))
}