package au.com.deanpike.ui.screen.detail

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.HiltTestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun show_detail_screen() = runTest {
        hiltRule.inject()

        composeTestRule.setContent {
            MviExampleTheme {
                DetailScreen()
            }
        }
    }
}