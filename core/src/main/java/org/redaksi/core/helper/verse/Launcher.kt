package org.redaksi.core.helper.verse

import android.content.Context
import android.content.Intent
import android.net.Uri

object Launcher {
    val TAG = Launcher::class.java.simpleName

    /**
     * Returns an intent that can be used to open the app at the specific book, chapter, and verse.
     * Call [Context.startActivity] with the returned intent from your activity to open it.
     * @param bookId 0 for Genesis, up to 65 for Revelation
     * @param chapter_1 Chapter number starting from 1
     * @param verse_1 Verse number starting from 1
     */
    fun openAppAtBibleLocation(bookId: Int, chapter_1: Int, verse_1: Int): Intent {
        val ari = bookId shl 16 or (chapter_1 shl 8) or verse_1
        return openAppAtBibleLocation(ari)
    }

    /**
     * Returns an intent that can be used to open the app at the specific ari.
     * Call [Context.startActivity] with the returned intent from your activity to open it.
     */
    fun openAppAtBibleLocation(ari: Int): Intent {
        val res = Intent("yuku.alkitab.action.VIEW")
        res.putExtra("ari", ari)
        res.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        return res
    }

    /**
     * Returns an intent that can be used to open the app at the specific ari.
     * The verse that is represented in the ari is selected.
     * Call [Context.startActivity] with the returned intent from your activity to open it.
     */
    fun openAppAtBibleLocationWithVerseSelected(ari: Int): Intent {
        val res = Intent("yuku.alkitab.action.VIEW")
        res.putExtra("ari", ari)
        res.putExtra("selectVerse", true)
        res.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        return res
    }

    /**
     * Returns an intent that can be used to open the Google Play app on the page for the user to download the app.
     * Call [Context.startActivity] with the returned intent from your activity to open it.
     * @param context any context of your app
     * @param product one of the product to open
     */
    fun openGooglePlayDownloadPage(context: Context, product: Product): Intent {
        val uri = Uri.parse(
            "market://details?id="
                + product.packageName
                + "&referrer=utm_source%3Dintegration%26utm_medium%3D"
                + context.packageName
        )
        val res = Intent(Intent.ACTION_VIEW)
        res.data = uri
        res.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        return res
    }

    enum class Product(val displayName: String, val packageName: String) {
        ALKITAB("Alkitab", "yuku.alkitab"), QUICK_BIBLE("Quick Bible", "yuku.alkitab.kjv");
    }
}
