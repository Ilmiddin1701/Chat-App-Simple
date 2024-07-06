plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.ilmiddin1701.chatapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ilmiddin1701.chatapp"
        minSdk = 24
        //noinspection OldTargetApi
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding { enable = true }
}

dependencies {

    implementation(libs.androidx.activity.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.squareup.picasso)

    //FireBase
    implementation(libs.firebase.auth)
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    //noinspection GradleDependency,UseTomlInstead
    implementation("com.google.android.gms:play-services-auth:19.2.0")

    //Save photo implementation
    //noinspection UseTomlInstead
    implementation ("com.google.zxing:core:3.4.1")
    //noinspection UseTomlInstead
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")


    //noinspection UseTomlInstead
    implementation("com.google.code.gson:gson:2.10.1")
}