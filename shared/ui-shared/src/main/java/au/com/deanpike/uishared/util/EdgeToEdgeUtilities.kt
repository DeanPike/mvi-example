package au.com.deanpike.uishared.util

import android.app.Activity
import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBarAppearance(useDarkIcons: Boolean) {
    val view = LocalView.current
    val activity = LocalActivity.current

    SideEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            activity?.let { checkedActivity ->
                val window = checkedActivity.window
                // Configure the appearance of the navigation bar
                window.isNavigationBarContrastEnforced = true

                // Configure the appearance of the status bar icons and text
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkIcons
            }
        }
    }
}

@Composable
fun SetupStatusBar(activity: Activity) {
    val darkMode = isSystemInDarkTheme()
    val window = activity.window

    val insetsController = WindowInsetsControllerCompat(window, window.decorView)
    insetsController.isAppearanceLightStatusBars = !darkMode
}

@Composable
fun StatusBarScrim(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    height: Float = WindowInsets.statusBars.getTop(LocalDensity.current).toFloat()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(with(LocalDensity.current) { height.toDp() })
            .zIndex(1f) // Put it above the background, below foreground content
            .background(color = color.copy(alpha = 0.64F))
    )
}

@Composable
fun StatusBarGradient(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    height: Float = WindowInsets.statusBars.getTop(LocalDensity.current).toFloat()
) {
    val brush = Brush.verticalGradient(
        colors = listOf(
            color.copy(alpha = 0.64f),
            color.copy(alpha = 0.0f),
        ),
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(with(LocalDensity.current) { height.toDp() })
            .zIndex(1f) // Put it above the background, below foreground content
            .background(brush = brush)
    )
}

@Composable
fun NavigationBarScrim(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    height: Float = WindowInsets.navigationBars.getTop(LocalDensity.current).toFloat()
) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    // Adjust status and navigation bar appearance
    val insetsController = WindowInsetsControllerCompat(window, window.decorView)
    insetsController.isAppearanceLightNavigationBars = true
    window.isNavigationBarContrastEnforced = true
}