plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id ("kotlin-kapt")
}

android {
  namespace = "com.cmsbando.erp"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.cmsbando.erp"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    vectorDrawables {
      useSupportLibrary = true
    }
    multiDexEnabled = true
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.6"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  buildToolsVersion = "34.0.0"
  ndkVersion = "25.1.8937393"
}

dependencies {
  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
  implementation("androidx.activity:activity-compose:1.8.1")
  implementation(platform("androidx.compose:compose-bom:2023.10.01"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation ("com.github.farhanroy:compose-awesome-dialog:1.0.1")
  implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
  implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
  implementation("androidx.navigation:navigation-compose:2.7.5")
  implementation ("com.github.Gurupreet:FontAwesomeCompose:1.1.0")
  implementation("io.coil-kt:coil-compose:2.5.0")
  implementation ("com.google.dagger:hilt-android:2.49")
  kapt ("com.google.dagger:hilt-android-compiler:2.49")
  implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
  kapt ("androidx.hilt:hilt-compiler:1.1.0")
  implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")
  debugImplementation("androidx.compose.ui:ui-tooling")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
}