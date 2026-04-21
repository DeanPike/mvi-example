package au.com.deanpike.mviexample.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import au.com.deanpike.navigation.keys.DefaultDetailScreenKey
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uishared.util.MviWindowWidthSizeClassProvider
import au.com.deanpike.uishared.util.SetupStatusBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var backStack: NavBackStack<NavKey>

    @Inject
    lateinit var appEntryBuilder: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>

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
                onDispose {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }
            }

            if (!MviWindowWidthSizeClassProvider.isCompactWidth()) {
                backStack.add(DefaultDetailScreenKey)
            }

            MviExampleTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) { paddingValues ->
                    ApplicationScreen(
                        modifier = Modifier.padding(paddingValues),
                        backStack = backStack,
                        appEntryBuilder = appEntryBuilder
                    )
                }
            }
        }
    }
}