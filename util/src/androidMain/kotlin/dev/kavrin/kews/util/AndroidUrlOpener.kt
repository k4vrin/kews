package dev.kavrin.kews.util

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Android implementation of UrlOpener
 */
class AndroidUrlOpener(private val context: Context) : UrlOpener {
    override fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            // Log error or handle failure silently
            println("Failed to open URL: $url. Error: ${e.message}")
        }
    }
}
