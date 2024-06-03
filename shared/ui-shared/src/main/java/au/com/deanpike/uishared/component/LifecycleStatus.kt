package au.com.deanpike.uishared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8

@Composable
fun LifecycleStatus(
    modifier: Modifier = Modifier,
    position: Int = 0,
    lifecycleStatus: String?
) {
    lifecycleStatus?.let {
        Box(
            modifier = modifier
                .padding(start = DIM_4, top = DIM_4)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(DIM_8)
                ),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                modifier = Modifier
                    .padding(DIM_4)
                    .testTag("${LifecycleStatusTestTags.LIFECYCLE_STATUS}$position"),
                text = it
            )
        }
    }
}

object LifecycleStatusTestTags {
    const val LIFECYCLE_STATUS = "LIFECYCLE_STATUS_"
}