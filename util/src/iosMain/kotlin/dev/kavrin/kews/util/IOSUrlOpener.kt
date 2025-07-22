package dev.kavrin.kews.util

import co.touchlab.kermit.Logger
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

/**
 * iOS implementation of UrlOpener
 */
class IOSUrlOpener : UrlOpener {
    private val logger = Logger.withTag("IOSUrlOpener")
    override fun openUrl(url: String) {
        try {
            val app = UIApplication.sharedApplication
            val nsURL = NSURL.URLWithString(url)

            // Check if the device can handle the URL first
            if (nsURL == null || !app.canOpenURL(nsURL)) {
                logger.e { "Device cannot handle mail URL" }
                return
            }

            var success = false
            app.openURL(
                nsURL,
                options = mapOf<Any?, Any>(),
                completionHandler = { result ->
                    success = result
                    if (result) {
                        logger.d { "Successfully opened mail URL" }
                    } else {
                        logger.e { "Failed to open mail URL: No handler for URL scheme" }
                    }
                }
            )

        } catch (e: Exception) {
            logger.e { "Exception when opening mail URL: ${e.message}" }
        }
    }
}
