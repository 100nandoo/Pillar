package org.redaksi.core.helper

import java.math.BigDecimal
import java.math.RoundingMode
import org.jsoup.Jsoup

/**
 * Calculates the reading time of the given [text].
 *
 * Based on [Medium's calculation](https://blog.medium.com/read-time-and-you-bc2048ab620c).
 *
 * @param text The text to be evaluated.
 * @param wpm The words per minute reading average.
 * @param postfix The value to be appended to the reading time.
 * @param plural The value to be appended if the reading time is more than 1 minute.
 * @param excludeImages Images are excluded from the reading time when set.
 * @param extra Additional seconds to be added to the total reading time.
 */
class ReadingTime @JvmOverloads constructor(
    text: String,
    wpm: Int = 250,
    var postfix: String = "menit",
    var plural: String = "menit",
    excludeImages: Boolean = false,
    extra: Int = 0
) {
    companion object {
        private const val INVALID: Double = -1.0

        /**
         * Counts words.
         *
         * HTML tags are stripped.
         */
        @JvmStatic
        fun wordCount(words: String): Int {
            val s = Jsoup.parse(words).text().trim()
            return if (s.isBlank()) 0 else s.split("\\s+".toRegex()).size
        }

        /**
         * Counts HTML img tags.
         */
        @JvmStatic
        fun imgCount(html: String): Int {
            return "<img ".toRegex(RegexOption.IGNORE_CASE).findAll(html).count()
        }
    }

    private var readTime: Double = INVALID

    var text: String = text
        set(value) {
            reset(value != text)
            field = value
        }

    var wpm: Int = wpm
        set(value) {
            reset(value != wpm)
            field = value
        }

    var excludeImages: Boolean = excludeImages
        set(value) {
            reset(value != excludeImages)
            field = value
        }

    var extra: Int = extra
        set(value) {
            reset(value != extra)
            field = value
        }

    /**
     * Calculates and returns the reading time in seconds.
     */
    private fun calcReadingTimeInSec(): Double {
        if (readTime == INVALID) {
            readTime = if (!excludeImages) calcImgReadingTime().toDouble() else 0.0
            readTime += (wordCount(text) / wpm) * 60.0
        }

        return readTime + extra
    }

    /**
     * Calculates and returns the reading time. (eg. 1 min read)
     */
    fun calcReadingTime(): String {
        val time = BigDecimal((calcReadingTimeInSec() / 60.0)).setScale(0, RoundingMode.HALF_DOWN)
        return if (time.compareTo(BigDecimal.ONE) == 1) {
            "$time $plural".trim()
        } else {
            "$time $postfix".trim()
        }
    }

    private fun calcImgReadingTime(): Int {
        var time = 0
        val imgCount = imgCount(text)

        var offset = 12
        for (i in 1..imgCount) {
            if (i > 10) {
                time += 3
            } else {
                time += offset
                offset--
            }
        }

        return time
    }

    private fun reset(isChanged: Boolean) {
        if (isChanged) readTime = INVALID
    }
}
