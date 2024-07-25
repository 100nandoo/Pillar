package org.redaksi.ui.lainnya

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.redaksi.ui.Dimens.EIGHT
import org.redaksi.ui.Dimens.SIXTEEN
import org.redaksi.ui.R
import org.redaksi.ui.compose.PillarColor
import org.redaksi.ui.compose.PillarTypography3

@Composable
fun LainnyaScreen(paddingValues: PaddingValues, onClickTentang: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PillarColor.background)
                .padding(it)
                .padding(paddingValues)
                .padding(SIXTEEN.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onClickTentang() }
                    .padding(0.dp, SIXTEEN.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(EIGHT.dp, 0.dp)
                        .fillMaxHeight(),
                    painter = painterResource(id = R.drawable.ic_tentang),
                    contentDescription = stringResource(R.string.tentang)
                )
                Text(
                    modifier = Modifier
                        .height(26.dp)
                        .fillMaxWidth(),
                    style = PillarTypography3.headlineSmall,
                    color = PillarColor.utamaTitle,
                    text = stringResource(id = R.string.tentang)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LainnyaScreenPreview() {
    LainnyaScreen(PaddingValues()) {}
}
