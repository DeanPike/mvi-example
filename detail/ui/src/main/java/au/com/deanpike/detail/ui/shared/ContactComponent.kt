package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun ContactComponent(
    modifier: Modifier = Modifier,
    label: String,
    value: String?,
    testTag: String
) {
    Row(
        modifier = modifier
            .padding(start = DIM_8, end = DIM_4)
            .testTag(testTag)
    ) {
        Text(
            modifier = Modifier
                .defaultMinSize(minWidth = 55.dp)
                .testTag("${testTag}_LABEL"),
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier
                .padding(start = DIM_4)
                .testTag("${testTag}_VALUE"),
            text = value ?: "",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactComponentPreview() {
    MviExampleTheme {
        ContactComponent(
            label = "Label",
            value = "Number",
            testTag = "TestTag"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactComponentWithNoValuePreview() {
    MviExampleTheme {
        ContactComponent(
            label = "Label",
            value = null,
            testTag = "TestTag"
        )
    }
}