plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)  // No version here!
    alias(libs.plugins.kotlin.serialization)  // No version here!
    alias(libs.plugins.kotlin.compose)  // No version here!
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.gms.google.services)  // Using alias for Hilt too
}

android {
    namespace = "com.abhinav.shoppingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.abhinav.shoppingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase BoM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Firebase Storage
    implementation("com.google.firebase:firebase-storage")

    // Other Firebase dependencies you might have
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // Coil
    implementation(libs.coil.compose)

    // Navigation
    implementation(libs.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Accompanist Pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // Razorpay
    implementation(libs.razorpay.checkout)

    // Custom Bottom Nav Bar
    implementation(libs.animated.navigationbar)

    implementation("androidx.navigation:navigation-compose:2.7.7")  // 🚀 Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
}

kapt {
    correctErrorTypes = true
}