package au.com.deanpike.uishared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun AnimatedPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    visibleDotCount: Int = 7
) {
    val currentPage = pagerState.currentPage
    val offset = pagerState.currentPageOffsetFraction

    val halfVisible = visibleDotCount / 2
    val startIndex = when {
        currentPage <= halfVisible -> 0
        currentPage >= pageCount - halfVisible -> (pageCount - visibleDotCount).coerceAtLeast(0)
        else -> currentPage - halfVisible
    }

    val endIndex = (startIndex + visibleDotCount).coerceAtMost(pageCount)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in startIndex until endIndex) {
            val distanceFromCenter = abs(i - currentPage - offset)
            val scale = 1f - distanceFromCenter.coerceIn(0f, 1f) * 0.5f
            val color = lerp(Color.White, Color.LightGray, distanceFromCenter.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size((8.dp * scale).coerceAtLeast(4.dp))
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}