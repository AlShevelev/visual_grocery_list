plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias { libs.plugins.ksp }
}

kotlin {
    jvmToolchain(11)
}

android {
    namespace = "com.shevelev.visualgrocerylist.database"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.room.runtime)
    ksp(libs.room.ksp)
    implementation(libs.room.ktx)

    implementation(libs.koin.android)
}