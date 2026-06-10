package au.com.deanpike.uishared.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uishared.theme.Dimension
import au.com.deanpike.uishared.util.ThemePreviews

@Composable
fun DetailItemComponent(
    @DrawableRes icon: Int,
    text: String,
    @StringRes description: Int,
    testTag: String
) {
    Row(
        modifier = Modifier.testTag(testTag)
    ) {
        Icon(
            modifier = Modifier
                .drawableTestTag(
                    tag = "${testTag}_ICON",
                    id = icon
                ),
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = description),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier
                .padding(start = Dimension.DIM_4)
                .testTag("${testTag}_TEXT"),
            color = MaterialTheme.colorScheme.onBackground,
            text = text
        )
    }
}

@Composable
@ThemePreviews
fun DetailItemComponentPreview() {
    AppTheme {
        DetailItemComponent(
            icon = R.drawable.baseline_bed_24,
            text = "Data",
            description = R.string.number_of_bedrooms,
            testTag = "TEST_TAG"
        )
    }
}