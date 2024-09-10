package au.com.deanpike.uishared.component

import au.com.deanpike.uishared.R
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.DetailItemComponentRobot
import org.junit.Test

class DetailItemComponentTest : UiUnitTestBase() {
    private val robot = DetailItemComponentRobot(composeTestRule)

    @Test
    fun check_that_details_items_are_displayed() {
        robot
            .setupComponent()
            .waitForIdle()
            .assertLayoutDisplayed()
            .assertIconDisplayed(
                testTag = "DETAIL_ITEM_COMPONENT",
                drawable = R.drawable.bed_outline,
                contentDescription = "Number of bedrooms"
            )
            .assertItemCount(
                testTag = "DETAIL_ITEM_COMPONENT",
                "3"
            )
    }
}