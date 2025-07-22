package dev.kavrin.kews.util

actual fun getPlatformName(): String = "Android ${android.os.Build.VERSION.SDK_INT}"