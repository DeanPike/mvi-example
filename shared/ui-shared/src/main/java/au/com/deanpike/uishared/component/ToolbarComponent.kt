package au.com.deanpike.uishared.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.component.ToolbarComponentTestTags.TOOLBAR_ICON
import au.com.deanpike.uishared.component.ToolbarComponentTestTags.TOOLBAR_LAYOUT
import au.com.deanpike.uishared.component.ToolbarComponentTestTags.TOOLBAR_TITLE
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.MviExampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarComponent(
    title: String,
    onBackClicked: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.testTag(TOOLBAR_LAYOUT),
        title = {
            Text(
                modifier = Modifier
                    .padding(
                        end = DIM_16
                    )
                    .testTag(TOOLBAR_TITLE),
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClicked
            ) {
                Icon(
                    modifier = Modifier
                        .drawableTestTag(
                            tag = TOOLBAR_ICON,
                            id = R.drawable.arrow_back_24
                        ),
                    painter = painterResource(R.drawable.arrow_back_24), contentDescription = stringResource(R.string.back)
                )
            }
        }
    )
}

object ToolbarComponentTestTags {
    private const val PREFIX = "TOOLBAR_"
    const val TOOLBAR_LAYOUT = "${PREFIX}LAYOUT"
    const val TOOLBAR_TITLE = "${PREFIX}TITLE"
    const val TOOLBAR_ICON = "${PREFIX}ICON"
}

@Preview
@Composable
fun ToolbarComponentPreview() {
    MviExampleTheme {
        ToolbarComponent(title = "Title")
    }
}