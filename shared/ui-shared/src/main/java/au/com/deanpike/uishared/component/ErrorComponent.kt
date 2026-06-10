package au.com.deanpike.uishared.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_BUTTON
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_LAYOUT
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_MESSAGE
import au.com.deanpike.uishared.component.ErrorComponentTestTags.ERROR_COMPONENT_TITLE
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.util.ThemePreviews

@Composable
fun ErrorComponent(
    onRetryClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(DIM_16)
            .testTag(ERROR_COMPONENT_LAYOUT),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.testTag(ERROR_COMPONENT_TITLE),
            text = stringResource(id = R.string.error_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.testTag(ERROR_COMPONENT_MESSAGE),
            text = stringResource(id = R.string.error_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Button(
            modifier = Modifier
                .padding(top = DIM_8)
                .testTag(ERROR_COMPONENT_BUTTON),
            onClick = { onRetryClicked() }) {
            Text(
                modifier = Modifier.testTag("${ERROR_COMPONENT_BUTTON}_TEXT"),
                text = stringResource(id = R.string.retry),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

object ErrorComponentTestTags {
    private const val PREFIX = "ERROR_COMPONENT_"
    const val ERROR_COMPONENT_LAYOUT = "${PREFIX}LAYOUT"
    const val ERROR_COMPONENT_TITLE = "${PREFIX}TITLE"
    const val ERROR_COMPONENT_MESSAGE = "${PREFIX}MESSAGE"
    const val ERROR_COMPONENT_BUTTON = "${PREFIX}BUTTON"
}

@ThemePreviews
@Composable
fun ErrorComponentPreview() {
    AppTheme {
        ErrorComponent()
    }
}