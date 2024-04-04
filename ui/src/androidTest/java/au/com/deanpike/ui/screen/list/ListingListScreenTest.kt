package au.com.deanpike.ui.screen.list

import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiTestBase
import org.junit.Test

class ListingListScreenTest : UiTestBase() {
    @Test
    fun show_listings() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreen()
                }
            }
            advanceTimeAndWait(this)
        }
    }
}