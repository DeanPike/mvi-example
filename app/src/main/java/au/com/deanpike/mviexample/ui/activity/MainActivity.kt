package au.com.deanpike.mviexample.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uishared.util.SetupStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val activity = this

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Adjust status and navigation bar appearance
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
        insetsController.isAppearanceLightNavigationBars = true

        // Optional: Set specific colors for status and navigation bars
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        setContent {
            SetupStatusBar(this)
            val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

            DisposableEffect(windowSizeClass) {

                if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                } else {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }

                onDispose {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }
            }

            MviExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ApplicationScreen()
                }
            }
        }
    }
}