package org.redaksi.ui.artikel.detail

import android.graphics.Typeface
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarColor.categoryTranskrip
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.PillarColor.surface
import org.redaksi.ui.PillarTypography
import org.redaksi.ui.R
import org.redaksi.ui.Symbol.bullet
import org.redaksi.ui.edisi.detail.detailScreenDate
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtikelDetailScreen(
    onClickKomentar: (Int) -> Unit
) {
    val viewModel: ArtikelDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val artikelId = remember { viewModel.artikelId }
    val bottomBarIcons = remember {
        listOf(
            BottomBarIcon(R.drawable.ic_cari, R.string.cari) {  },
            BottomBarIcon(R.drawable.ic_komentar, R.string.komentar) { onClickKomentar(it) },
            BottomBarIcon(R.drawable.ic_share, R.string.share) {  }
        )
    }
    Scaffold(
        bottomBar = {
            ArtikelDetailBottomBar(bottomBarIcons, artikelId)
        }
    ) {
        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                ArtikelHeader(artikelDetailUi = uiState.articleDetailUi)
                ArtikelBody(modifier = Modifier.padding(it), artikelDetailUi = uiState.articleDetailUi)
            }
        }
    }
}

@Composable
fun ArtikelDetailBottomBar(bottomBarIcons: List<BottomBarIcon>, artikelId: Int?) {
    BottomAppBar(
        modifier = Modifier.clip(RoundedCornerShape(sixteen.dp, sixteen.dp, 0.dp, 0.dp)),
        containerColor = primary,
    ) {
        Row(horizontalArrangement = Arrangement.End){
            Spacer(modifier = Modifier.weight(1f))
            bottomBarIcons.forEach { bottomBarIcon ->
                BottomBarItem(bottomBarIcon = bottomBarIcon, artikelId = artikelId)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtikelDetailBottomBarPreview() {
    ArtikelDetailBottomBar(listOf(
        BottomBarIcon(R.drawable.ic_cari, R.string.cari) {  },
        BottomBarIcon(R.drawable.ic_komentar, R.string.komentar) {  },
        BottomBarIcon(R.drawable.ic_share, R.string.share) {  }
    ), 0)
}

@Composable
fun BottomBarItem(bottomBarIcon: BottomBarIcon, artikelId: Int?) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(primary)
            .clickable { artikelId?.let { bottomBarIcon.onClick(artikelId) } }
            .padding(sixteen.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(bottomBarIcon.icon),
            contentDescription = stringResource(bottomBarIcon.label),
            tint = surface
        )
    }
}

val category = CategoryUi("Transkrip", R.drawable.ic_transkrip)
val artikelDetailUi = ArtikelDetailUi(
    "Iman, Pengharapan, dan Kasih (Bagian 16): Doktrin Iman",
    "Adam R",
    Date(),
    "12 menit",
    category,
    "Iman adalah hal yang sangat unik, khususnya dalam agama Kristen, karena Alkitab berkata, “Tanpa iman, tidak ada orang yang " +
        "diperkenan Allah.” Manusia beriman dan menjadi orang yang diperkenan Tuhan. Iman tidak berarti kita menyatakan jasa keyakinan kita dan " +
        "cukup syarat sehingga Tuhan harus terima. Justru iman membuktikan dan mengaku bahwa kita tidak berjasa, tidak layak, tidak berharga, dan " +
        "tidak bersyarat, kemudian datang kepada Tuhan, bersandar kepada-Nya, dan menerima jasa Yesus menjadi sumber iman kita. \n"
)

@Composable
fun ArtikelHeader(artikelDetailUi: ArtikelDetailUi) {
    Column(
        Modifier
            .background(PillarColor.artikelDetailTitleBackground)
            .padding(sixteen.dp)
    ) {
        Text(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, eight.dp),
            style = PillarTypography.titleLarge,
            text = artikelDetailUi.title,
            textAlign = TextAlign.Center
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(style = PillarTypography.labelSmall, text = artikelDetailUi.authors)
            Text(style = PillarTypography.labelSmall, text = " $bullet ")
            Text(style = PillarTypography.labelSmall, text = detailScreenDate(LocalContext.current, artikelDetailUi.date))
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(eight.dp))
                    .background(PillarColor.secondaryVar)
                    .padding(8.dp),
                style = PillarTypography.bodySmall,
                text = artikelDetailUi.estimation
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtikelHeaderPreview() {
    ArtikelHeader(
        artikelDetailUi = artikelDetailUi
    )
}

@Composable
fun ArtikelBody(modifier: Modifier, artikelDetailUi: ArtikelDetailUi) {
    Column(
        modifier
            .background(PillarColor.background)
            .padding(sixteen.dp)
    ) {
        ArtikelKategori(categoryUi = artikelDetailUi.categoryUi)
        AndroidView(
            modifier = Modifier.padding(0.dp, eight.dp, 0.dp, 0.dp),
            factory = { context ->
                TextView(context).apply {
                    setLineSpacing(12f, 1f)
                    textSize = 16f
                    val typeface: Typeface? = ResourcesCompat.getFont(context, R.font.lato_regular)
                    setTypeface(typeface)
                    setTextColor(ResourcesCompat.getColor(resources, R.color.primary, null))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        justificationMode = JUSTIFICATION_MODE_INTER_WORD
                    }
                }
            },
            update = { it.text = HtmlCompat.fromHtml(artikelDetailUi.body, HtmlCompat.FROM_HTML_MODE_COMPACT) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtikelBodyPreview() {
    ArtikelBody(Modifier, artikelDetailUi)
}

@Composable
fun ArtikelKategori(categoryUi: CategoryUi) {
    Row(
        modifier = Modifier
            .padding(0.dp, 0.dp, eight.dp, 0.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(categoryTranskrip)
            .padding(eight.dp)

    ) {
        Icon(painter = painterResource(id = categoryUi.icon), contentDescription = categoryUi.label)
        Text(categoryUi.label)
    }
}

@Preview(showBackground = true)
@Composable
fun ArtikelKategoriPreview() {
    ArtikelKategori(CategoryUi("Transkrip", R.drawable.ic_transkrip))
}
