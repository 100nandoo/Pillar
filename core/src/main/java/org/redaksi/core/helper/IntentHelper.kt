package org.redaksi.core.helper

import android.content.Intent

object IntentHelper {

    /**
     * Creates an Intent to launch the Android Sharesheet to share the given text content.
     *
     * @param title The title to be displayed in the Sharesheet.
     * @param text The text content to be shared.
     * @return An Intent that can be used to launch the Sharesheet.
     */
    fun createShareTextIntent(title: String, text: String): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        return Intent.createChooser(sendIntent, null)
    }
}
