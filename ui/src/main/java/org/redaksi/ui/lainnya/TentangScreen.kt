package org.redaksi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData

@Composable
fun TentangScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val state = rememberWebViewStateWithHTMLData(TENTANG_HTML)
        WebView(
            modifier = Modifier
                .padding(it),
            state = state
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TentangScreenPreview() {
    TentangScreen()
}

const val TENTANG_HTML = """
<html>
<body style="background-color:#FCF5F3;">
<style>
body {
  padding:8px;
}
</style>
<h2>Aplikasi Buletin Pillar</h2>

<p><a href="http://www.buletinpillar.org">www.buletinpillar.org</a></p>

<p><b>Pengembang:</b>
    <br><a href="https://www.halim.dev">Fernando Fransisco Halim</a>
    <br>Randy Sugianto
</p>

<p><b>Warna Aplikasi:</b>
    <br>Maria Fransiska
</p>

<hr/>

<h2>Tujuan dari penerbitan Pillar</h2>

<ol>
    <li>Membawa pemuda untuk menghidupkan signifikansi gerakan Reformed Injili di dalam segala bidang.
    <li>Berperan sebagai wadah edukasi & informasi yang menjawab kebutuhan pemuda.
</ol>

<h3>Penasihat:</h3>
<p>
        Pdt. Benyamin F. Intan
    <br>Pdt. Sutjipto Subeno
</p>

<h3>Redaksi:</h3>
<p><b>Pemimpin Redaksi:</b> Ev. Edward Oei
    <br><b>Wakil Pemimpin Redaksi:</b> Ev. Diana Ruth
    <br><b>Redaksi Pelaksana:</b> Adhya Kumara, Heruarto Salim, Heryanto Tjandra
    <br><b>Desain:</b> Mellisa Gunawan, Michael Leang
    <br><b>Redaksi Bahasa:</b> Darwin Kusuma, Juan Intan Kanggrawan, Lukas Yuan Utomo, Mildred Sebastian, Yana Valentina
    <br><b>Redaksi Umum:</b> Budiman Thia, Erwan, Hadi Salim Suroso, Randy Sugianto, Yesaya Ishak
</p>

<hr/>

<h2>Tata Cara Pengutipan</h2>

<p>Pembaca bebas untuk memberikan hyperlink di blog atau website pribadi kalian ke situs Pillar -  www.buletinpillar.org  </p>

<p>Sejak edisi pertama, Buletin Pillar terus berusaha mempertahankan kualitas dan mempertanggungjawabkan isi sebaik mungkin. Setiap artikel telah diperiksa oleh hamba Tuhan yang juga bertanggung jawab. Sehingga, setiap artikel memiliki basis gerakan Reformed Injili yang kuat. Oleh karena itu bila artikel ini dipakai oleh pihak lain, maka artikel ini dapat kami pertanggung jawabkan dan mempunyai stand point yang jelas. </p>

<p>Bagi pembaca yang mendapatkan berkat melalui artikel-artikel dan ingin mengutip kalimat-kalimat yang ada di dalam artikel Pillar untuk hasil karya tulisan kalian? Beberapa hal yang terkait dengan pengutipan agar sesuai dengan kode etik hak cipta, silakan ikuti tata cara pengutipan di bawah:</p>

<ul>
    <li>Kutipan dari artikel buletin Pillar tidak diubah isi</li>
    <li>Harus mencantumkan nama penulis artikel yang dikutip </li>
    <li>Harus mencantumkan nara sumber yang lengkap</li>
</ul>

<p>contoh:
    <br/>Dikutip dari Buletin Pillar (Buletin Pemuda Gereja Reformed Injili Indonesia) edisi no. <i>XXX (bulan, tahun)</i>, artikel <i>(judul artikel)</i> oleh <i>(penulis)</i>.
    <br/>Bahkan anda bisa memuat alamat website kami, http://www.buletinpillar.org</p>

<hr/>

<p>Redaksi Pillar menantikan setiap masukan, saran, maupun kritik yang membangun baik itu untuk isi/konten artikel, desain maupun aplikasi Pillar.
   Anda bisa menyampaikannya melalui <a href="mailto:redaksi@buletinpillar.org">redaksi@buletinpillar.org</a></p>

</body>
</html>
"""
