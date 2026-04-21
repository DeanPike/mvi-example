package au.com.deanpike.uishared.util

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
object MviWindowWidthSizeClassProvider {

    @Composable
    fun get(): MviWindowSize {
        if (LocalInspectionMode.current) {
            return DpSize(
                width = LocalWindowInfo.current.containerDpSize.width,
                height = LocalWindowInfo.current.containerDpSize.height,
            ).mapToMviWindowWidthSizeClass()
        }

        return LocalActivity.current?.let {
            calculateWindowSizeClass(it).widthSizeClass.mapToLocalWindowWidthSizeClass()
        } ?: MviWindowSize.COMPACT
    }

    private fun WindowWidthSizeClass.mapToLocalWindowWidthSizeClass(): MviWindowSize? {
        return when (this) {
            WindowWidthSizeClass.Compact -> MviWindowSize.COMPACT
            WindowWidthSizeClass.Medium -> MviWindowSize.MEDIUM
            WindowWidthSizeClass.Expanded -> MviWindowSize.EXPANDED
            else -> null
        }
    }

    // align with the internal function: androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.fromWidth
    private fun DpSize.mapToMviWindowWidthSizeClass(): MviWindowSize {
        require(width >= 0.dp) { "Width must not be negative" }
        return when {
            width < 600.dp -> MviWindowSize.COMPACT
            width < 840.dp -> MviWindowSize.MEDIUM
            else -> MviWindowSize.EXPANDED
        }
    }

    @Composable
    fun isCompactWidth() = get() == MviWindowSize.COMPACT

    @Composable
    fun isExpandedWidth() = get() == MviWindowSize.EXPANDED

    @Composable
    fun isFoldable(): Boolean {
        return LocalActivity.current?.let {
            val layoutInfo by WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it).collectAsState(initial = null)
            layoutInfo?.mapToDisplayFeatures()
        } ?: false
    }

    private fun WindowLayoutInfo.mapToDisplayFeatures(): Boolean {
        // checker to see if device has folding feature (e.g. HINGE)
        return this.displayFeatures.isNotEmpty() && ((this.displayFeatures[0] is FoldingFeature))
    }
}

val LocalMviWindowWidthSizeClass = staticCompositionLocalOf { MviWindowSize.COMPACT }

enum class MviWindowSize {
    COMPACT, MEDIUM, EXPANDED
}


