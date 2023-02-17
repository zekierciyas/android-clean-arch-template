object Versions {
    const val androidApplication = "7.1.13"
    const val androidLibrary = "7.1.13"
    const val gradleNavigationArgVersion = "2.3.5"
    const val kotlinAndroid = "1.6.21"
    const val kotlinJvm = "1.6.21"
    const val ktLintVersion = "10.2.0"
    const val core = "1.9.0"
    const val appcompat = "1.5.1"
    const val androidMaterial = "1.6.1"
    const val constraintLayout = "2.1.4"
    const val testImplJunit = "4.13.2"
    const val androidTestImplJunit = "1.1.3"
    const val androidTestEspresso = "3.4.0"
    const val kotlinCoroutines = "1.6.1"
}

object DependencyCatalog {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
}

object TestImplementation {
    const val junit = "junit:junit:${Versions.testImplJunit}"
}

object AndroidTestImplementation {
    const val junit = "androidx.test.ext:junit:${Versions.androidTestImplJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
}

object Plugins {
    const val androidApplication = "com.android.application:7.1.3"
    const val androidLibrary = "com.android.library:7.1.3"
    const val kotlinAndroid = "org.jetbrains.kotlin.android:1.6.21"
    const val kotlinJvm = "org.jetbrains.kotlin.jvm:1.6.21"

}


