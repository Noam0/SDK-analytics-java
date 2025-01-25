plugins {
    alias(libs.plugins.android.library)
    id("maven-publish") // ✅ Required for JitPack
}

android {
    namespace = "com.example.analyticslibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
}

// ✅ Ensure 'release' component is recognized
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                 // 🔥 Use "default" instead of "release"
                groupId = "com.github.Noam0"
                artifactId = "SDK-analytics-java"
                version = "1.0"

                artifact(tasks.getByName("bundleReleaseAar"))

                pom {
                    name.set("SDK Analytics Java")
                    description.set("An analytics SDK for Android applications.")
                    url.set("https://github.com/Noam0/SDK-analytics-java")
                }
            }
        }
    }
}
