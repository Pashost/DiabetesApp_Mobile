plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(34)
    buildToolsVersion("34.0.0")

    defaultConfig {
        applicationId = "com.example.diabetes_app_mobile"
        minSdkVersion(18)
        targetSdkVersion(34)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        namespace = "com.example.diabetes_app_mobile" // Specify your namespace here
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("com.google.firebase:firebase-database:20.0.0") // Firebase Realtime Database
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.activity:activity:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation(libs.swiperefreshlayout)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
