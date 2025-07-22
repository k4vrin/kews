package util

object AppConfig {
    const val APPLICATION_ID = "dev.kavrin.kews"
    const val COMPILE_SDK = 36
    const val MIN_SDK = 23
    const val TARGET_SDK = 36
    const val VERSION_MAJOR = 0
    const val VERSION_MINOR = 0
    const val VERSION_PATCH = 1
    const val VERSION_CLASSIFIER = "alpha01" // Can be empty, e.g. "alpha", "beta", "rc", etc.
    const val BUILD_NUMBER = 1

    val VERSION_CODE = generateVersionCode()
    val VERSION_NAME = generateVersionName()

    private fun generateVersionCode(): Int {
        return VERSION_MAJOR * 10000 + VERSION_MINOR * 1000 + VERSION_PATCH * 100 + BUILD_NUMBER
    }


    private fun generateVersionName(): String {
        var versionName = "$VERSION_MAJOR.$VERSION_MINOR.$VERSION_PATCH"
        if (VERSION_CLASSIFIER.isNotEmpty()) {
            versionName = "$versionName-$VERSION_CLASSIFIER"
        }
        return versionName
    }
}