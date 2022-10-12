package org.redaksi.core.helper

import android.content.Intent

object IntentHelper {
    fun shareSheetIntent(title: String, text: String): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        return Intent.createChooser(sendIntent, null)
    }
}
