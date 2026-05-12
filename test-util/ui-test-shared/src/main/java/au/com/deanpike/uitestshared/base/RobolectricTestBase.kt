package au.com.deanpike.uitestshared.base

import android.os.Build
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RoborazziRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.BAKLAVA], qualifiers = "w412dp-h915dp")
abstract class RobolectricTestBase {

    // To run robolectric tests with roborazzi, run the following command on the command line
    // ./gradlew :listings:ui:testDebugUnitTest recordRoborazziDebug

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val roborazziRule = RoborazziRule(
        composeRule = composeTestRule,
        captureRoot = composeTestRule.onRoot(),
        options = RoborazziRule.Options(
            outputDirectoryPath = "build/outputs/roborazzi",
            captureType = RoborazziRule.CaptureType.LastImage(onlyFail = true) // Captures the state at the moment of failure
        )
    )
}