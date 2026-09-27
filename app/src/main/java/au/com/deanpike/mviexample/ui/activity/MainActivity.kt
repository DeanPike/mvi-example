package au.com.deanpike.mviexample.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.navigation.di.resetToStartDestination
import au.com.deanpike.uishared.theme.AppTheme
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
        // The manifest locks the activity to portrait so a phone opened on its side is never drawn
        // in landscape. Android 16+ ignores that lock on large screens (smallest width >= 600dp);
        // lift it here too so tablets and unfolded foldables on older versions can still rotate.
        // Always set it explicitly: the requested orientation survives activity recreation, so a
        // foldable that is unfolded then folded again must be re-locked to portrait.
        requestedOrientation = if (resources.configuration.smallestScreenWidthDp >= LARGE_SCREEN_MIN_WIDTH_DP) {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        // Back on the last screen closes the app. Without this, Android 12-15 only moves a root
        // activity to the background, and reopening would resume on the listing screen instead of
        // going through onCreate below. Registered before setContent so NavDisplay's own back
        // handling (added later, so higher priority) still pops screens while there is more than one.
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        // A fresh launch always starts on search. Recreation after a configuration change (fold,
        // tablet rotation) passes a saved state and keeps the user where they were.
        if (savedInstanceState == null) {
            backStack.resetToStartDestination()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Adjust status and navigation bar appearance
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
        insetsController.isAppearanceLightNavigationBars = true

        setContent {
            SetupStatusBar(this)
            AppTheme {
                ApplicationScreen(
                    backStack = backStack,
                    appEntryBuilder = appEntryBuilder
                )
            }
        }
    }
}

private const val LARGE_SCREEN_MIN_WIDTH_DP = 600
