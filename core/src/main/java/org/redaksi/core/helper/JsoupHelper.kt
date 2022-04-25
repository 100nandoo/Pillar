package org.redaksi.core.helper

import org.jsoup.Jsoup

object JsoupHelper {
    fun stripText(text: String): String {
        return Jsoup.parse(text).text()
    }
}
