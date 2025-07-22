package dev.kavrin.kews.util

/**
 * Interface for opening URLs in the system browser
 */
interface UrlOpener {
    /**
     * Opens a URL in the platform's default browser
     * @param url The URL to open
     */
    fun openUrl(url: String)
}
