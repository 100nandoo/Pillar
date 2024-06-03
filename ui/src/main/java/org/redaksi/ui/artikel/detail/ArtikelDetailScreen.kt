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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import org.redaksi.core.helper.IntentHelper
import org.redaksi.core.helper.verse.AlkitabIntegrationUtil
import org.redaksi.core.helper.verse.ConnectionResult
import org.redaksi.core.helper.verse.DesktopVerseFinder
import org.redaksi.core.helper.verse.DesktopVerseParser
import org.redaksi.core.helper.verse.Launcher
import org.redaksi.core.helper.verse.VerseProvider
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.R
import org.redaksi.ui.R.font.pt_serif_regular
import org.redaksi.ui.compose.Inter
import org.redaksi.ui.compose.PillarColor
import org.redaksi.ui.compose.PillarColor.background
import org.redaksi.ui.compose.PillarColor.kategori
import org.redaksi.ui.compose.PillarColor.secondary
import org.redaksi.ui.compose.PillarTypography3
import org.redaksi.ui.compose.UiModelProvider

@Composable
fun ArtikelDetailScreen() {
    val viewModel: ArtikelDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val wrappedClickEvent = WrappedClickEvent(
        showKnownDialog = { isShown, verse, ari -> viewModel.onEvent(ArtikelDetailEvent.ShowKnownDialog(isShown, verse, ari)) },
        showNotKnownDialog = { viewModel.onEvent(ArtikelDetailEvent.ShowNotKnownDialog(it)) },
        dismissNotKnownDialog = { viewModel.onEvent(ArtikelDetailEvent.DismissNotKnownDialog) },
        installDialog = { viewModel.onEvent(ArtikelDetailEvent.InstallDialog(it)) },
        playStoreDialog = { viewModel.onEvent(ArtikelDetailEvent.PlayStoreDialog(it)) }
    )

    ArtikelDetailScreenContent(uiState = uiState, scrollState = scrollState, context = context, wrappedClickEvent)
}

data class WrappedClickEvent(
    val showKnownDialog: (Boolean, AnnotatedString, Int) -> Unit = { _, _, _ -> },
    val showNotKnownDialog: (String) -> Unit = {},
    val dismissNotKnownDialog: () -> Unit = {},
    val installDialog: (Boolean) -> Unit = {},
    val playStoreDialog: (Boolean) -> Unit = {}
)

@Composable
fun ArtikelDetailScreenContent(
    uiState: ArtikelDetailViewModelState,
    scrollState: ScrollState,
    context: Context,
    wrappedClickEvent: WrappedClickEvent
) {
    fun share(title: String, text: String) {
        context.startActivity(IntentHelper.shareSheetIntent(title, text))
    }
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            AnimatedVisibility(
                visible = scrollState.value == scrollState.maxValue || scrollState.value == 0,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(
                    onClick = { share(uiState.articleDetailUi.title, uiState.articleDetailUi.webUrl) },
                    containerColor = secondary,
                    contentColor = background
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(R.string.share)
                    )
                }
            }
        }
    ) {
        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            Column(
                Modifier
                    .background(background)
                    .verticalScroll(scrollState)
            ) {
                val isImageExist = uiState.articleDetailUi.imageUrl.isNotBlank()
                if (isImageExist) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(uiState.articleDetailUi.imageUrl)
                            .crossfade(true)
                            .build(),
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
                    { wrappedClickEvent.showNotKnownDialog(it) },
                    installDialog = { wrappedClickEvent.installDialog(true) },
                    known = { isShown, verse, ari -> wrappedClickEvent.showKnownDialog(isShown, verse, ari) }
                )

                if (uiState.showNotKnownDialog.first) {
                    NotKnownVerseDialog(verse = uiState.showNotKnownDialog.second, dismissDialog = { wrappedClickEvent.dismissNotKnownDialog() })
                }

                if (uiState.showAlkitabInstalledDialog) {
                    AlkitabDialog(dismissDialog = { wrappedClickEvent.installDialog(false) }, {
                        val isSuccess =
                            runCatching { context.startActivity(Launcher.openGooglePlayDownloadPage(context, Launcher.Product.ALKITAB)) }.getOrNull()
                        wrappedClickEvent.playStoreDialog(isSuccess == null)
                    })
                }

                if (uiState.showPlayStoreDialog) {
                    PlayStoreDialog {
                        wrappedClickEvent.playStoreDialog(false)
                    }
                }

                if (uiState.showKnownDialog.first) {
                    VerseDialog(uiState.showKnownDialog.third, uiState.showKnownDialog.second, {
                        runCatching { context.startActivity(Launcher.openAppAtBibleLocation(it)) }.getOrNull()
                    }) {
                        wrappedClickEvent.showKnownDialog(false, AnnotatedString(""), 0)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ArtikelDetailScreenPreview() {
    ArtikelDetailScreenContent(
        uiState = ArtikelDetailViewModelState(UiModelProvider.articleDetailUi, false),
        scrollState = ScrollState(0),
        context = LocalContext.current,
        wrappedClickEvent = WrappedClickEvent()
    )
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
                    containerColor = secondary
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

@Preview
@Composable
fun NotKnownVerseDialogPreview() {
    NotKnownVerseDialog(verse = "Yohanes 3:16") {}
}

@Composable
fun AlkitabDialog(dismissDialog: () -> Unit, openPlayStore: () -> Unit) {
    AlertDialog(
        containerColor = background,
        text = {
            Text(stringResource(id = R.string.aplikasi_alkitab_tidak_terinstal))
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondary
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
                    containerColor = secondary
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

@Preview
@Composable
fun AlkitabDialogPreview() {
    AlkitabDialog({}) {}
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
                    containerColor = secondary
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

@Preview
@Composable
fun PlayStoreDialogPreview() {
    PlayStoreDialog {}
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
                    containerColor = secondary
                ),
                onClick = {
                    dismissDialog()
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondary
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

@Preview
@Composable
fun VerseDialogPreview() {
    VerseDialog(0, AnnotatedString("Yohanes 3:16"), {}) {}
}

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
            FlowRow(Modifier.weight(1f)) {
                Text(
                    style = PillarTypography3.bodySmall,
                    fontStyle = Italic,
                    color = kategori,
                    text = artikelDetailUi.categoryUi.map { it.label }.joinToString(", ")
                )
                Text(style = PillarTypography3.bodySmall, fontStyle = Italic, text = " - ")
                Text(style = PillarTypography3.bodySmall, fontStyle = Italic, text = artikelDetailUi.authors)
            }
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
    ArtikelHeader(artikelDetailUi = UiModelProvider.articleDetailUi)
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
                                                        fontStyle = Italic,
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
                                        ds.color = Color.parseColor("#7B3334")
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
    ArtikelBody(Modifier, UiModelProvider.articleDetailUi, LocalContext.current, {}, {}) { _, _, _ -> }
}
