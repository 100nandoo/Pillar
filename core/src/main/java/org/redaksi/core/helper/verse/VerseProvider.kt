package org.redaksi.core.helper.verse

import android.content.ContentResolver
import android.net.Uri

class VerseProvider(private val cr: ContentResolver) {
    class Verse {
        var _id: Long = 0
        var ari: Int = 0
        var bookName: String? = null
        var text: String? = null

        val bookId: Int
            get() = (0xff0000 and ari) shr 16

        val chapter: Int
            get() = (0x00ff00 and ari) shr 8

        val verse: Int
            get() = (0x0000ff and ari)

        override fun toString(): String {
            return bookName + " " + chapter + ":" + verse + " " + text
        }
    }

    class VerseRanges {
        private var ranges = IntArray(16)
        private var size = 0

        fun addRange(bookId_start: Int, chapter_1_start: Int, verse_1_start: Int, bookId_end: Int, chapter_1_end: Int, verse_1_end: Int) {
            val start = (bookId_start shl 16) or (chapter_1_start shl 8) or verse_1_start
            val end = (bookId_end shl 16) or (chapter_1_end shl 8) or verse_1_end

            addRange(start, end)
        }

        fun addRange(ari_start: Int, ari_end: Int) {
            if (ranges.size < size + 2) {
                val ranges_new = IntArray(ranges.size * 2)
                System.arraycopy(ranges, 0, ranges_new, 0, size)
                ranges = ranges_new
            }

            ranges[size++] = ari_start
            ranges[size++] = ari_end
        }

        override fun toString(): String {
            val sb = StringBuilder()
            var i = 0
            while (i < size) {
                if (sb.length != 0) {
                    sb.append(',')
                }
                sb.append(ranges[i]).append('-').append(ranges[i + 1])
                i += 2
            }
            return sb.toString()
        }
    }

    /**
     * Reads a single verse from the default version.
     * @param bookId 0 for Genesis, up to 65 for Revelation
     * @param chapter_1 Chapter number starting from 1
     * @param verse_1 Verse number starting from 1
     * @return null when failed or the requested verse
     */
    fun getVerse(bookId: Int, chapter_1: Int, verse_1: Int): Verse? {
        val ari = (bookId shl 16) or (chapter_1 shl 8) or (verse_1)
        return getVerse(ari)
    }

    /**
     * Reads a single verse from the default version.
     * @param ari a combination of bookId (byte 2), chapter_1 (byte 1) and verse_1 (byte 0) in an int (with byte number 3 for MSB and 0 for LSB).
     * @return null when failed or the requested verse
     */
    fun getVerse(ari: Int): Verse? {
        val c = cr.query(
            Uri.parse(("content://" + AlkitabIntegrationUtil.providerAuthority) + "/" + PATH_bible_verses_single_by_ari + ari + "?formatting=0"),
            null,
            null,
            null,
            null
        )
            ?: return null

        try {
            val col__id = c.getColumnIndexOrThrow("_id")
            val col_ari = c.getColumnIndexOrThrow(COLUMN_ari)
            val col_bookName = c.getColumnIndexOrThrow(COLUMN_bookName)
            val col_text = c.getColumnIndexOrThrow(COLUMN_text)

            if (c.moveToNext()) {
                val res = Verse()
                res._id = c.getLong(col__id)
                res.ari = c.getInt(col_ari)
                res.bookName = c.getString(col_bookName)
                res.text = c.getString(col_text)
                return res
            } else {
                return null
            }
        } finally {
            c.close()
        }
    }

    /**
     * Reads verses from the default version using verse ranges.
     * @return null when failed, empty when no verses satisfy the requested ranges, or verses from the requested ranges.
     */
    fun getVerses(ranges: VerseRanges): List<Verse>? {
        val c = cr.query(
            Uri.parse(("content://" + AlkitabIntegrationUtil.providerAuthority) + "/" + PATH_bible_verses_range_by_ari + ranges.toString() + "?formatting=0"),
            null,
            null,
            null,
            null
        )
            ?: return null

        try {
            val res: MutableList<Verse> = ArrayList()

            val col__id = c.getColumnIndexOrThrow("_id")
            val col_ari = c.getColumnIndexOrThrow(COLUMN_ari)
            val col_bookName = c.getColumnIndexOrThrow(COLUMN_bookName)
            val col_text = c.getColumnIndexOrThrow(COLUMN_text)

            while (c.moveToNext()) {
                val v = Verse()
                v._id = c.getLong(col__id)
                v.ari = c.getInt(col_ari)
                v.bookName = c.getString(col_bookName)
                v.text = c.getString(col_text)
                res.add(v)
            }

            return res
        } finally {
            c.close()
        }
    }

    companion object {
        val TAG: String = VerseProvider::class.java.simpleName

        const val PATH_bible_verses_single_by_lid: String = "bible/verses/single/by-lid/"
        const val PATH_bible_verses_single_by_ari: String = "bible/verses/single/by-ari/"
        const val PATH_bible_verses_range_by_lid: String = "bible/verses/range/by-lid/"
        const val PATH_bible_verses_range_by_ari: String = "bible/verses/range/by-ari/"

        const val COLUMN_ari: String = "ari"
        const val COLUMN_bookName: String = "bookName"
        const val COLUMN_text: String = "text"
    }
}
