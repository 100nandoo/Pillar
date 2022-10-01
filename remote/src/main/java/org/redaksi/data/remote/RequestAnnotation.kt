package org.redaksi.data.remote

import androidx.annotation.IntDef
import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    value = [
        TRANSKIP, ALKITAB_N_THEOLOGI, IMAN_KRISTEN, KEHIDUPAN_KRISTEN, RENUNGAN, ISU_TERKINI, SENI_BUDAYA, SEPUTAR_GRII, RESENSI,
        ARTIKEL_MINGGUAN, IMAN_KRISTEN_N_PEKERJAAN
    ],
    open = false
)
annotation class CategoryId

const val TRANSKIP = 27
const val ALKITAB_N_THEOLOGI = 314
const val IMAN_KRISTEN = 317
const val KEHIDUPAN_KRISTEN = 23
const val RENUNGAN = 526
const val ISU_TERKINI = 315
const val SENI_BUDAYA = 316
const val SEPUTAR_GRII = 53
const val RESENSI = 25
const val ARTIKEL_MINGGUAN = 527
const val IMAN_KRISTEN_N_PEKERJAAN = 317

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    value = [TITLE, BODY],
    open = false
)
annotation class SearchIn

const val TITLE = "title"
const val BODY = "body"
