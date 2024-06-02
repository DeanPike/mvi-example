package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags.DETAIL_APP_BAR_ADDRESS
import au.com.deanpike.detail.ui.shared.DetailAppBarComponentTestTags.DETAIL_APP_BAR_CLOSE_ICON
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun DetailAppBarComponent(
    address: String,
    onCloseClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DIM_16),
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

        Text(
            modifier = Modifier
                .padding(start = DIM_8)
                .testTag(DETAIL_APP_BAR_ADDRESS),
            text = address,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

object DetailAppBarComponentTestTags {
    private const val PREFIX = "DETAIL_APP_BAR_"
    const val DETAIL_APP_BAR_CLOSE_ICON = "${PREFIX}CLOSE_ICON"
    const val DETAIL_APP_BAR_ADDRESS = "${PREFIX}ADDRESS"
}

@Preview(showBackground = true)
@Composable
fun DetailAppBarPreview() {
    MviExampleTheme {
        DetailAppBarComponent("Address")
    }
}

@Preview(showBackground = true)
@Composable
fun DetailAppBarLongAddressPreview() {
    MviExampleTheme {
        DetailAppBarComponent("This is a very long address to check the screen layout. it has to be more than two lines long")
    }
}