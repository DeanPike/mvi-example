package au.com.deanpike.uishared.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GridOverlayPreviewComponent(
    colour: Color = Color.LightGray, // the colour of the grid line
    interval: Dp = 8.dp, // The space between the grid lines
    widthPx: Float = 1080F, // The screen width in pixels to use when not running the preview
    heightPx: Float = 2400F, // The screen height in pixels to use when not running the preview
    showVerticalMargins: Boolean = true, // Show the vertical margins
    verticalMarginColour: Color = Color.Blue.copy(alpha = 0.5F), // The vertical line colour
    verticalMarginSize: Dp = 16.dp, // The space between the edge of the screen and the margin
    content: @Composable () -> Unit = {} // The content to draw the grid over
) {
    val density = LocalDensity.current
    val intervalPx = with(density) { interval.toPx() }
    val isPreview = LocalInspectionMode.current
    val verticalMarginPx = with(density) { verticalMarginSize.toPx() }

    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }

        // Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .drawWithContent {
                    drawContent()
                    var x = 0F
                    var y = 0F
                    val height = if (isPreview) heightPx else size.height
                    val width = if (isPreview) widthPx else size.width

                    // Vertical lines
                    while (x + intervalPx < width) {
                        x += intervalPx
                        drawLine(
                            color = colour,
                            start = Offset(x = x, y = 0F),
                            end = Offset(x = x, y = height)
                        )
                    }
                    // Horizontal lines
                    while (y + intervalPx < height) {
                        y += intervalPx
                        drawLine(
                            color = colour,
                            start = Offset(x = 0F, y = y),
                            end = Offset(x = width, y = y)
                        )
                    }

                    if (showVerticalMargins) {
                        drawLine(
                            color = verticalMarginColour,
                            start = Offset(x = verticalMarginPx, y = 0F),
                            end = Offset(x = verticalMarginPx, y = height)
                        )
                        drawLine(
                            color = verticalMarginColour,
                            start = Offset(x = width - verticalMarginPx, y = 0F),
                            end = Offset(x = width - verticalMarginPx, y = height)
                        )
                    }
                }
        ) {
        }
    }
}