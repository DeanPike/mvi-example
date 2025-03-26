package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.PriceComponentRobot
import au.com.deanpike.uitestshared.robot.PriceComponentRobotInitData
import au.com.deanpike.uitestshared.util.disableAnimations
import org.junit.Before
import org.junit.Test

class PriceComponentTest : UiUnitTestBase() {
    private val robot = PriceComponentRobot(composeTestRule)

    @Before
    fun setupTest() {
        composeTestRule.disableAnimations()
    }

    @Test
    fun should_show_price_data() {
        robot
            .setupComponent(
                data = PriceComponentRobotInitData(price = "$1,000,000")
            )
            .assertLayoutDisplayed()
            .assertPriceLabelDisplayed()
            .assertPriceDataDisplayed("$1,000,000")
    }
}