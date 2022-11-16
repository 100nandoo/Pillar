package org.redaksi.ui.lainnya

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData

@OptIn(ExperimentalMaterial3Api::class)
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
<body style="background-color:#FCFAF7;">
<style>
body {
  padding:8px;
}
</style>
<h2>Aplikasi Buletin PILLAR</h2>

<p><a href="http://www.buletinpillar.org">www.buletinpillar.org</a></p>

<hr/>
<p><b>Tujuan dari penerbitan PILLAR</b>
<ol><li>Membawa pemuda untuk menghidupkan signifikansi gerakan Reformed Injili di dalam segala bidang.</li><li>Berperan sebagai wadah edukasi &amp; informasi yang menjawab kebutuhan pemuda.</li></ol>

<p><b>Pengurus Buletin PILLAR</b>
<br>Penasihat – Pdt. Benyamin F. Intan, Pdt. Sutjipto Subeno<br>Ketua – Pdt. Heruarto Salim<br>Sekretaris – Chai Su San<br>Bendahara – Yesaya Ishak
</p>

<p><b>Editorial:</b>
<br>Ketua – Pdt. Heruarto Salim<br>Editor Bahasa – Darwin Kusuma, Noah Sundah, Juan Intan Kanggrawan, Yana Valentina<br>Konten – Pdt. Heruarto Salim, Adhya Kumara, Kevin Nobel, Elya Wibowo, Juan Intan Kanggrawan, Erwan Zhang, Biya Hannah
</p>

<p><b>Android App:</b>
<br><a href="https://www.halim.dev">Fernando Fransisco Halim</a>
<br><a href="https://www.hencewijaya.com/">Hence Wijaya</a>
<br>Randy Sugianto
</p>

<p><b>Warna Aplikasi:</b>
<br>Maria Fransiska
</p>

<p><b>Website:</b>
<br>Ketua – Vik. Kenny Ruben<br>Desain Web – Andrian Cedric, Hosea Aldri<br>Programmer – Randy Sugianto, Hence Wijaya<br>Desain Grafis – Heryanto Tjandra, Michael Leang, Mellisa Gunawan
</p>

<p><b>Medsos:</b>
<br>Ketua – Vik. William Sugiarto<br>Konten – Vik. William Sugiarto, Hadi Salim Suroso, Kevin Nobel, Joanne Emmanuel<br>Desain – Leony Novita, Kezia Leonardo, Stella Yohanna, Eunice Gabrielle<br>Admin – Chai Su San
</p>

<hr class="wp-block-separator has-alpha-channel-opacity">

<p><b>Tata Cara Pengutipan</b>
<p>Pembaca bebas untuk memberikan hyperlink di blog atau website pribadi kalian ke situs Pillar – <a href="https://www.buletinpillar.org">www.buletinpillar.org</a></p>
<p>Sejak edisi pertama, Bulletin Pillar terus berusaha mempertahankan kualitas dan mempertanggungjawabkan isi sebaik mungkin. Setiap artikel telah diperiksa oleh hamba Tuhan yang juga bertanggung jawab. Sehingga, setiap artikel memiliki basis gerakan Reformed Injili yang kuat. Oleh karena itu bila artikel ini dipakai oleh pihak lain, maka artikel ini dapat kami pertanggung jawabkan dan mempunyai stand point yang jelas.</p>
<p>Bagi pembaca yang mendapatkan berkat melalui artikel-artikel dan ingin mengutip kalimat-kalimat yang ada di dalam artikel Pillar untuk hasil karya tulisan kalian? Beberapa hal yang terkait dengan pengutipan agar sesuai dengan kode etik hak cipta, silakan ikuti tata cara pengutipan di bawah:</p>
<ul><li>Kutipan dari artikel buletin Pillar tidak diubah isi</li><li>Harus mencantumkan nama penulis artikel yang dikutip</li><li>Harus mencantumkan nara sumber yang lengkap</li></ul>

<p><b>contoh:</b><br>Dikutip dari Bulletin Pillar (Bulletin Pemuda Gereja Reformed Injili Indonesia) edisi no.&nbsp;<em>XXX (bulan, tahun)</em>, artikel&nbsp;<em>(judul artikel)</em>&nbsp;oleh&nbsp;<em>(penulis)</em>.<br>Bahkan anda bisa memuat alamat website kami, <a href="https://www.buletinpillar.org">https://www.buletinpillar.org</a></p>
<!-- AddThis Advanced Settings above via filter on the_content --><!-- AddThis Advanced Settings below via filter on the_content --><!-- AddThis Advanced Settings generic via filter on the_content --><!-- AddThis Share Buttons above via filter on the_content --><!-- AddThis Share Buttons below via filter on the_content --><div class="at-below-post-page addthis_tool" data-url="https://www.buletinpillar.org/tentang-pillar"></div><!-- AddThis Share Buttons generic via filter on the_content -->
</div>

</body>
</html>
"""
