package org.redaksi.ui.artikel.detail

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_INCLUSIVE
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.redaksi.core.helper.verse.AlkitabIntegrationUtil
import org.redaksi.core.helper.verse.ConnectionResult
import org.redaksi.core.helper.verse.DesktopVerseFinder
import org.redaksi.core.helper.verse.DesktopVerseParser
import org.redaksi.core.helper.verse.Launcher
import org.redaksi.core.helper.verse.VerseProvider
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.Inter
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarColor.background
import org.redaksi.ui.PillarColor.kategori
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.PillarTypography3
import org.redaksi.ui.R
import org.redaksi.ui.R.font.pt_serif_regular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtikelDetailScreen() {
    val viewModel: ArtikelDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold {
        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            Column(
                Modifier
                    .background(background)
                    .verticalScroll(rememberScrollState())
            ) {
                val isImageExist = uiState.articleDetailUi.imageUrl.isNotBlank()
                if(isImageExist){
                    AsyncImage(
                        model = uiState.articleDetailUi.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f)
                    )
                }
                ArtikelHeader(artikelDetailUi = uiState.articleDetailUi)
                ArtikelBody(
                    modifier = Modifier.padding(it),
                    artikelDetailUi = uiState.articleDetailUi,
                    context,
                    { viewModel.showNotKnownDialog(it) },
                    installDialog = { viewModel.installDialog(true) },
                    known = { isShown, verse, ari -> viewModel.showKnownDialog(isShown, verse, ari) }
                )

                if (uiState.showNotKnownDialog.first) {
                    NotKnownVerseDialog(verse = uiState.showNotKnownDialog.second, dismissDialog = { viewModel.dismissNotKnownDialog() })
                }

                if (uiState.showAlkitabInstalledDialog) {
                    AlkitabDialog(dismissDialog = { viewModel.installDialog(false) }, {
                        val isSuccess =
                            runCatching { context.startActivity(Launcher.openGooglePlayDownloadPage(context, Launcher.Product.ALKITAB)) }.getOrNull()
                        viewModel.playStoreDialog(isSuccess == null)
                    })
                }

                if (uiState.showPlayStoreDialog) {
                    PlayStoreDialog {
                        viewModel.playStoreDialog(false)
                    }
                }

                if (uiState.showKnownDialog.first) {
                    VerseDialog(uiState.showKnownDialog.third, uiState.showKnownDialog.second, {
                        runCatching { context.startActivity(Launcher.openAppAtBibleLocation(it)) }.getOrNull()
                    }) {
                        viewModel.showKnownDialog(false)
                    }
                }
            }
        }
    }
}

@Composable
fun NotKnownVerseDialog(verse: String, dismissDialog: () -> Unit) {
    AlertDialog(
        containerColor = background,
        text = {
            Text("Tidak dapat mengenali $verse, silakan membukanya secara manual")
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                ),
                onClick = {
                    dismissDialog()
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }
        },
        onDismissRequest = {
            dismissDialog()
        }
    )
}

@Composable
fun AlkitabDialog(dismissDialog: () -> Unit, openPlayStore: () -> Unit) {
    AlertDialog(
        containerColor = background,
        text = {
            Text("Aplikasi Alkitab tidak terinstal. Instal Alkitab dari Play Store?")
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                ),
                onClick = {
                    openPlayStore()
                    dismissDialog()
                }
            ) {
                Text(stringResource(id = R.string.ya))
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                ),
                onClick = {
                    dismissDialog()
                }
            ) {
                Text(stringResource(id = R.string.tidak))
            }
        },
        onDismissRequest = {
            dismissDialog()
        }
    )
}

@Composable
fun PlayStoreDialog(dismissDialog: () -> Unit) {
    AlertDialog(
        containerColor = background,
        text = {
            Text("Play Store tidak tersedia")
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                ),
                onClick = {
                    dismissDialog()
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }
        },
        onDismissRequest = {
            dismissDialog()
        }
    )
}

@Composable
fun VerseDialog(ari: Int, verse: AnnotatedString, openBible: (Int) -> Unit, dismissDialog: () -> Unit) {
    AlertDialog(
        containerColor = background,
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(verse)
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                ),
                onClick = {
                    dismissDialog()
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                ),
                onClick = {
                    openBible(ari)
                    dismissDialog()
                }
            ) {
                Text(stringResource(id = R.string.buka_alkitab))
            }
        },
        onDismissRequest = {
            dismissDialog()
        }
    )
}

val category = listOf(CategoryUi("Transkrip", R.drawable.ic_transkrip))
val artikelDetailUi = ArtikelDetailUi(
    "Iman, Pengharapan, dan Kasih (Bagian 16): Doktrin Iman",
    "Adam R",
    displayDate = "1 Okt 2023",
    estimation = "12 menit",
    categoryUi = category,
    body = "Iman adalah hal yang sangat unik, khususnya dalam agama Kristen, karena Alkitab berkata, “Tanpa iman, tidak ada orang yang " +
        "diperkenan Allah.” Manusia beriman dan menjadi orang yang diperkenan Tuhan. Iman tidak berarti kita menyatakan jasa keyakinan kita dan " +
        "cukup syarat sehingga Tuhan harus terima. Justru iman membuktikan dan mengaku bahwa kita tidak berjasa, tidak layak, tidak berharga, dan " +
        "tidak bersyarat, kemudian datang kepada Tuhan, bersandar kepada-Nya, dan menerima jasa Yesus menjadi sumber iman kita. \n"
)

@Composable
fun ArtikelHeader(artikelDetailUi: ArtikelDetailUi) {
    Column(
        Modifier
            .background(PillarColor.primary)
            .padding(sixteen.dp)
    ) {
        Text(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, eight.dp),
            style = PillarTypography3.titleLarge,
            text = artikelDetailUi.title,
            color = PillarColor.artikelDetailTitle
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                style = PillarTypography3.bodySmall,
                fontStyle = Italic,
                color = kategori,
                text = artikelDetailUi.categoryUi.map { it.label }.joinToString(",")
            )
            Text(style = PillarTypography3.bodySmall, fontStyle = Italic, text = " - ")
            Text(style = PillarTypography3.bodySmall, fontStyle = Italic, text = artikelDetailUi.authors)
            Spacer(Modifier.weight(1f))
            Text(
                style = PillarTypography3.bodySmall,
                fontStyle = Italic,
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
fun ArtikelBody(
    modifier: Modifier,
    artikelDetailUi: ArtikelDetailUi,
    context: Context,
    notKnown: (String) -> Unit,
    installDialog: () -> Unit,
    known: (Boolean, AnnotatedString, Int) -> Unit
) {
    Column(
        modifier
            .background(background)
    ) {
        AndroidView(
            modifier = Modifier.padding(sixteen.dp, eight.dp, sixteen.dp, 0.dp),
            factory = { context ->
                TextView(context).apply {
                    setLineSpacing(12f, 1f)
                    textSize = 16f
                    val typeface: Typeface? = ResourcesCompat.getFont(context, pt_serif_regular)
                    movementMethod = LinkMovementMethod.getInstance()
                    setTypeface(typeface)
                    setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
                }
            },
            update = {
                (it.parent as? ViewGroup)?.clipChildren = false
                val sb = SpannableStringBuilder(HtmlCompat.fromHtml(artikelDetailUi.body, HtmlCompat.FROM_HTML_MODE_LEGACY))
                val provider = VerseProvider(context.contentResolver)
                DesktopVerseFinder.findInText(
                    sb,
                    object : DesktopVerseFinder.DetectorListener {
                        override fun onVerseDetected(start: Int, end: Int, verse: String?): Boolean {
                            sb.setSpan(
                                object : ClickableSpan() {
                                    override fun onClick(p0: View) {
                                        val intArrayList = DesktopVerseParser.verseStringToAri(verse)
                                        if (intArrayList == null) {
                                            notKnown(verse ?: context.getString(R.string.ayat_ini))
                                            return
                                        }

                                        if (AlkitabIntegrationUtil.isIntegrationAvailable(context) != ConnectionResult.SUCCESS) {
                                            installDialog()
                                            return
                                        }
                                        val ranges = VerseProvider.VerseRanges()
                                        for (i in 0 until intArrayList.size()) {
                                            ranges.addRange(intArrayList[i], intArrayList[i + 1])
                                        }

                                        val verses = provider.getVerses(ranges)
                                        val dialogSpan = AnnotatedString.Builder()
                                        if (verses != null) {
                                            for (item in verses) {
                                                dialogSpan.withStyle(
                                                    style = SpanStyle(
                                                        fontStyle = FontStyle.Italic,
                                                        fontFamily = Inter
                                                    )
                                                ) {
                                                    val ayatKitab = "${item.bookName} ${item.chapter}:${item.verse}  "
                                                    append(ayatKitab)
                                                }
                                                dialogSpan.append(item.text)
                                                dialogSpan.append("\n\n")
                                                sb.setSpan(ForegroundColorSpan(Color.BLACK), start, end, SPAN_EXCLUSIVE_INCLUSIVE)
                                            }
                                        }
                                        known(true, dialogSpan.toAnnotatedString(), intArrayList.get(0))
                                    }

                                    override fun updateDrawState(ds: TextPaint) {
                                        ds.isUnderlineText = false
                                        ds.color = Color.parseColor("#E28E78")
                                    }
                                },
                                start,
                                end,
                                SPAN_INCLUSIVE_INCLUSIVE
                            )
                            return true
                        }

                        override fun onNoMoreDetected() = Unit
                    }
                )
                it.text = sb
            }
        )
        Text(
            modifier = Modifier.padding(sixteen.dp, 0.dp, sixteen.dp, sixteen.dp),
            style = PillarTypography3.labelMedium,
            text = artikelDetailUi.displayDate
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtikelBodyPreview() {
    ArtikelBody(Modifier, artikelDetailUi, LocalContext.current, {}, {}) { _, _, _ -> }
}
