package au.com.deanpike.ui.screen.shared

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.ui.R
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.theme.Dimension
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun DetailItemComponent(
    @DrawableRes icon: Int,
    text: String,
    @StringRes description: Int,
    testTag: String,
    position: Int
) {
    Row(
        modifier = Modifier.testTag("${testTag}_$position")
    ) {
        Icon(
            modifier = Modifier
                .drawableTestTag(
                    tag = "${testTag}_ICON_$position",
                    id = icon
                ),
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = description),

            )
        Text(
            modifier = Modifier
                .padding(start = Dimension.DIM_4)
                .testTag("${testTag}_TEXT_$position"),
            text = text
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DetailItemComponentPreview() {
    MviExampleTheme {
        DetailItemComponent(
            icon = R.drawable.bed_outline,
            text = "Data",
            description = R.string.number_of_bedrooms,
            testTag = "TEST_TAG",
            position = 0
        )
    }
}