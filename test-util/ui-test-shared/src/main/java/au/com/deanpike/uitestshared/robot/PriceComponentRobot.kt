package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.PriceComponent
import au.com.deanpike.uishared.component.PriceComponentTestTags.PRICE_COMPONENT_DATA
import au.com.deanpike.uishared.component.PriceComponentTestTags.PRICE_COMPONENT_LABEL
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class PriceComponentRobot(composeRule: ComposeContentTestRule) :
    TestRobotBase<PriceComponentRobot, PriceComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: PriceComponentRobotInitData?) = apply {
        composeRule.setContent {
            AppTheme {
                PriceComponent(
                    price = data!!.price
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(PRICE_COMPONENT_LABEL)
    }

    fun waitForLayoutToBeDisplayed() = apply {
        composeRule.waitUntilTagExists(PRICE_COMPONENT_LABEL)
    }

    fun assertPriceLabelDisplayed() = apply {
        composeRule.assertTextDisplayed(tag = PRICE_COMPONENT_LABEL, text = "Price")
    }

    fun assertPriceDataDisplayed(price: String) = apply {
        composeRule.assertTextDisplayed(tag = PRICE_COMPONENT_DATA, text = price)
    }
}

data class PriceComponentRobotInitData(
    val price: String = ""
) : TestRobotInitData