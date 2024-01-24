plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = ProjectSettings.namespace("networking")
    compileSdk = ProjectSettings.compileSdk

    defaultConfig {
        minSdk = ProjectSettings.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        val apiKey = providers.environmentVariable("NEWS_API_KEY").getOrElse("")
        debug {
            buildConfigField("String", "NEWS_API_KEY", """"$apiKey"""")
        }
        release {
            buildConfigField("String", "NEWS_API_KEY", """"$apiKey"""")
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
    buildFeatures {
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = ProjectSettings.Versions.jvmTarget
    }
}

dependencies {
    implementation(libs.core.ktx)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.loggingInterceptor)
    implementation(libs.converter.gson)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.paging.runtime)

    testImplementation(libs.junit)
}