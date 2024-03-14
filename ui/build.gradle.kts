@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.anvil)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 29

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
    val javaVersion = libs.versions.java.get()
    compileOptions {
        sourceCompatibility(javaVersion)
        targetCompatibility(javaVersion)
    }

    kotlinOptions {
        jvmTarget = javaVersion
    }
    buildFeatures {
        viewBinding = true
        compose = true

    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
}

dependencies {

    implementation(project(":domain"))


    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.viewmodel)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(platform(libs.compose.bom))
    implementation (libs.androidx.activity)
    implementation (libs.compose.material3)
    implementation (libs.androidX.lifecycle.runtime.compose)
    implementation(libs.androidX.lifecycle.viewModel)



    /* Retrofit */
    implementation (libs.retrofit.lib)
    implementation (libs.retrofit.gsonConverter)

    /* Dagger2 y anvil*/
    implementation (libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.anvil.annotations)


    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}