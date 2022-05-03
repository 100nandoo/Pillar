package org.redaksi.data.remote

import androidx.annotation.IntDef
import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    value = [LAIN_LAIN, TRANSKIP, ARTIKEL, PERKARA, QA, RESENSI, LIPUTAN, PROFILE, RENUNGAN],
    open = false
)
annotation class CategoryId

const val LAIN_LAIN = -1
const val TRANSKIP = 1
const val ARTIKEL = 2
const val PERKARA = 3
const val QA = 4
const val RESENSI = 5
const val LIPUTAN = 6
const val PROFILE = 7
const val MEJA_REDAKSI = 8
const val POKOK_DOA = 10
const val RENUNGAN = 17

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    value = [TITLE, BODY],
    open = false
)
annotation class SearchIn

const val TITLE = "title"
const val BODY = "body"
