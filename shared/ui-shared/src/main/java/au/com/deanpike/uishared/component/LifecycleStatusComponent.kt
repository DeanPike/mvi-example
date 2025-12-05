package au.com.deanpike.uishared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.uishared.component.LifecycleStatusTestTags.LIFECYCLE_STATUS_LAYOUT
import au.com.deanpike.uishared.component.LifecycleStatusTestTags.LIFECYCLE_STATUS_TEXT
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun LifecycleStatusComponent(
    modifier: Modifier = Modifier,
    lifecycleStatus: String?
) {
    lifecycleStatus?.let {
        Box(
            modifier = modifier
                .padding(start = DIM_4, top = DIM_4)
                .wrapContentWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(DIM_8)
                )
                .testTag(LIFECYCLE_STATUS_LAYOUT),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                modifier = Modifier
                    .padding(DIM_4)
                    .testTag(LIFECYCLE_STATUS_TEXT),
                text = it,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

object LifecycleStatusTestTags {
    private const val PREFIX = "LIFECYCLE_STATUS_"
    const val LIFECYCLE_STATUS_LAYOUT = "${PREFIX}LAYOUT"
    const val LIFECYCLE_STATUS_TEXT = "${PREFIX}TEST"
}

@Preview
@Composable
fun LifecycleStatusComponentPreview(){
    MviExampleTheme() {
        LifecycleStatusComponent(
            lifecycleStatus = "New"
        )
    }
}