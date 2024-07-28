package au.com.deanpike.mviexample.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import au.com.deanpike.mviexample.ui.screen.MviCompactApp
import au.com.deanpike.mviexample.ui.screen.MviMediumApp
import au.com.deanpike.uishared.theme.MviExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

            MviExampleTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                        MviCompactApp()
                    } else {
                        MviMediumApp()
                    }
                }
            }
        }
    }
}