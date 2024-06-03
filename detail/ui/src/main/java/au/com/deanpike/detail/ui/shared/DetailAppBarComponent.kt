package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags.DETAIL_APP_BAR_CLOSE_ICON
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun DetailAppBarComponent(
    onCloseClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onCloseClicked() }) {
            Icon(
                modifier = Modifier.drawableTestTag(
                    tag = DETAIL_APP_BAR_CLOSE_ICON,
                    id = R.drawable.clear_24
                ),
                painter = painterResource(id = R.drawable.clear_24),
                contentDescription = stringResource(id = R.string.close)
            )
        }

    }
}

object DetailAppBarComponentTestTags {
    private const val PREFIX = "DETAIL_APP_BAR_"
    const val DETAIL_APP_BAR_CLOSE_ICON = "${PREFIX}CLOSE_ICON"
}

@Preview(showBackground = true)
@Composable
fun DetailAppBarPreview() {
    MviExampleTheme {
        DetailAppBarComponent()
    }
}