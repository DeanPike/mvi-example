package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.PriceComponent
import au.com.deanpike.uishared.component.PriceComponentTestTags.PRICE_COMPONENT_DATA
import au.com.deanpike.uishared.component.PriceComponentTestTags.PRICE_COMPONENT_LABEL
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class PriceComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<PriceComponentRobot, PriceComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: PriceComponentRobotInitData?): PriceComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                PriceComponent(
                    price = data!!.price
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): PriceComponentRobot {
        composeRule.assertTagDisplayed(PRICE_COMPONENT_LABEL)
        return this
    }

    fun waitForLayoutToBeDisplayed(): PriceComponentRobot {
        composeRule.waitUntilTagExists(PRICE_COMPONENT_LABEL)
        return this
    }

    fun assertPriceLabelDisplayed(): PriceComponentRobot {
        composeRule.assertTextDisplayed(tag = PRICE_COMPONENT_LABEL, text = "Price")
        return this
    }

    fun assertPriceDataDisplayed(price: String): PriceComponentRobot {
        composeRule.assertTextDisplayed(tag = PRICE_COMPONENT_DATA, text = price)
        return this
    }
}

data class PriceComponentRobotInitData(
    val price: String = ""
) : TestRobotInitData