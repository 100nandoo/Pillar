package org.redaksi.ui.compose

import org.redaksi.data.remote.ALKITAB_N_THEOLOGI
import org.redaksi.data.remote.IMAN_KRISTEN
import org.redaksi.data.remote.ISU_TERKINI
import org.redaksi.data.remote.KEHIDUPAN_KRISTEN
import org.redaksi.data.remote.RENUNGAN
import org.redaksi.data.remote.RESENSI
import org.redaksi.data.remote.SENI_BUDAYA
import org.redaksi.data.remote.SEPUTAR_GRII
import org.redaksi.data.remote.TRANSKIP
import org.redaksi.ui.R
import org.redaksi.ui.artikel.Page
import org.redaksi.ui.artikel.detail.ArtikelDetailUi
import org.redaksi.ui.artikel.detail.CategoryUi
import org.redaksi.ui.utama.ArticleUi

object UiModelProvider {
    val pageList = listOf(
        Page(R.string.transkrip, TRANSKIP),
        Page(R.string.alkitab_theologi, ALKITAB_N_THEOLOGI),
        Page(R.string.iman_kristen_pekerjaan, IMAN_KRISTEN),
        Page(R.string.kehidupan_kristen, KEHIDUPAN_KRISTEN),
        Page(R.string.renungan, RENUNGAN),
        Page(R.string.isu_terkini, ISU_TERKINI),
        Page(R.string.seni_budaya, SENI_BUDAYA),
        Page(R.string.seputar_grii, SEPUTAR_GRII),
        Page(R.string.resensi, RESENSI)
    )

    val articleUiList = listOf(
        ArticleUi(
            id = 4309,
            title = "Iman, Pengharapan, dan Kasih (Bagian 24): Kasih (4)",
            "1 Korintus 13:1-3 1 Korintus 13 adalah pasal penting di dalam Alkitab yang berbicara tentang kasih, khususnya dalam Perjanjian " +
                "Baru. Pasal 13 ayat pertama menulis bahwa kasih bukan pintar berbicara.",
            authors = "Pdt. Dr. Stephen Tong",
            displayDate = "1 Oct 2022",
            imageUrl = "https://i0.wp.com/www.buletinpillar.org/wp-content/uploads/2022/09/iman-pengharapan-kasih.webp?fit=1920%2C1080&ssl=1"
        ),
        ArticleUi(
            id = 4342,
            title = "Serigala di Antara Kita",
            "Belakangan ini, masyarakat Indonesia sedang terkaget-kaget tentang kasus kekerasan dan pembunuhan yang dilakukan dalam " +
                "sebuah institusi kepolisian. Pada saat ini juga, investigasi terhadap pelaku dan jaringan kejahatan sedang berlangsung dan",
            authors = "Kevin Nobel",
            displayDate = "7 Oct 2022",
            imageUrl = "https://i0.wp.com/www.buletinpillar.org/wp-content/uploads/2022/10/serigala-di-antara-kita.jpg?fit=1179%2C768&ssl=1"
        ),

        ArticleUi(
            id = 4281,
            title = "Kebajikan, Kesalehan, Kasih: Penderitaan – 2",
            "Legend of the White Snake (Bai She Chuan) atau Ular Putih termasuk ke dalam 4 cerita rakyat teragung dari negeri Tiongkok. " +
                "Berbeda dengan Journey to the West (Xi You Ji) atau Kera Sakti yang",
            authors = "Lukas Yuan Utomo",
            displayDate = "27 Sep 2022",
            imageUrl = "https://i0.wp.com/www.buletinpillar.org/wp-content/uploads/2022/09/Kebajikan-Kesalehan-Kasih.jpg?fit=1144%2C334&ssl=1"
        )
    )

    val categoryUi = CategoryUi("Isu terkini")

    val articleDetailUi = ArtikelDetailUi(
        title = "Serigala di Antara Kita",
        authors = "Kevin Nobel",
        displayDate = "7 Oct 2022",
        estimation = "6 menit",
        categoryUi = listOf(categoryUi),
        body = "Belakangan ini, masyarakat Indonesia sedang terkaget-kaget tentang kasus kekerasan dan pembunuhan yang dilakukan dalam sebuah " +
            "institusi kepolisian. Pada saat ini juga, investigasi terhadap pelaku dan jaringan kejahatan sedang berlangsung dan",
        bodyStriped = "Belakangan ini, masyarakat Indonesia sedang terkaget-kaget tentang kasus kekerasan dan pembunuhan yang dilakukan dalam" +
            "sebuah institusi kepolisian. Pada saat ini juga, investigasi terhadap pelaku dan jaringan kejahatan sedang berlangsung dan",
        imageUrl = "https://i0.wp.com/www.buletinpillar.org/wp-content/uploads/2022/10/serigala-di-antara-kita.jpg?fit=1179%2C768&ssl=1"
    )
}
