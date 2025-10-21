package au.com.deanpike.uishared.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

fun Modifier.shadowElevationOne(
    shadowShape: Shape
) = shadow(
    elevation = 2.dp,
    spotColor = Color(0xFF131212).copy(alpha = 0.3F),
    ambientColor = Color(0xFF131212).copy(alpha = 0.5F),
    shape = shadowShape
)

fun Modifier.shadowElevationTwo(
    shadowShape: Shape
) = shadow(
    elevation = 4.dp,
    spotColor = Color(0xFF131212).copy(alpha = 0.4F),
    ambientColor = Color(0xFF131212).copy(alpha = 0.5F),
    shape = shadowShape
)

fun Modifier.shadowElevationThree(
    shadowShape: Shape
) = shadow(
    elevation = 6.dp,
    spotColor = Color(0xFF131212).copy(alpha = 0.5F),
    ambientColor = Color(0xFF131212).copy(alpha = 0.5F),
    shape = shadowShape
)

fun Modifier.shadowElevationFour(
    shadowShape: Shape
) = shadow(
    elevation = 8.dp,
    spotColor = Color(0xFF131212).copy(alpha = 0.4F),
    ambientColor = Color(0xFF131212).copy(alpha = 0.4F),
    shape = shadowShape
)