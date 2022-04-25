package org.redaksi.ui.lainnya

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarTypography3
import org.redaksi.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LainnyaScreen(paddingValues: PaddingValues, onClickTanggapan: () -> Unit, onClickTentang: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PillarColor.background)
                .padding(paddingValues)
                .padding(sixteen.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .clickable { onClickTanggapan() }
                        .padding(0.dp, sixteen.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(eight.dp, 0.dp),
                        painter = painterResource(id = R.drawable.ic_komentar), contentDescription = stringResource(R.string.tanggapan)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = PillarTypography3.headlineSmall,
                        color = PillarColor.edisiDetailTitle,
                        text = stringResource(id = R.string.tanggapan)
                    )

                }
                Row(
                    modifier = Modifier
                        .clickable { onClickTentang() }
                        .padding(0.dp, sixteen.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(eight.dp, 0.dp),
                        painter = painterResource(id = R.drawable.ic_tentang), contentDescription = stringResource(R.string.tentang)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = PillarTypography3.headlineSmall,
                        color = PillarColor.edisiDetailTitle,
                        text = stringResource(id = R.string.tentang)
                    )

                }
            }
        }
    }
}
