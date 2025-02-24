package au.com.deanpike.uishared.util

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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