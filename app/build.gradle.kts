plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //    id("kotlin-kapt")
}

android {
    namespace = "com.falcon.evcharger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.falcon.evcharger"
        minSdk = 24
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        //        sourceCompatibility = JavaVersion.VERSION_17
        //        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "1.8"
        //        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //Dependency for appcompat
    implementation("androidx.appcompat:appcompat:1.6.1")

    //Dependency for Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //Dependency for okHttp
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    // Dependency JSON Parsing
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Dependency constraintlayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //Dependency for material design
    implementation("com.google.android.material:material:1.11.0")
    // Dependency for constraintlayout
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    //Dependency for Event Bus
    implementation("org.greenrobot:eventbus:3.2.0")

    //    implementation("androidx.activity:activity-ktx:1.4.0")
    //    implementation("androidx.fragment:fragment-ktx:1.4.0")
    //    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'

    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")

    //Flag based number for phone_input
    implementation("com.hbb20:ccp:2.5.0")
    //    implementation ("com.hbb20:ccp:1.7.1")

    //    implementation("androidx.databinding:databinding-runtime:8.2.2")

    //    implementation("me.relex:circleindicator:2.1.6")

    implementation("androidx.viewpager2:viewpager2:1.0.0-alpha04")
    // for QR code Scanner
    //    implementation("me.dm7.barcodescanner:zxing:1.9.13")
}