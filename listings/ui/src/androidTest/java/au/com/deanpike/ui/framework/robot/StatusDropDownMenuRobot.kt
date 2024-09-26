package au.com.deanpike.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.ui.screen.list.component.FilterComponentTestTags.STATUS_ITEM
import au.com.deanpike.ui.screen.list.component.FilterComponentTestTags.STATUS_ITEM_MENU
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class StatusDropDownMenuRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<StatusDropDownMenuRobot, TestRobotInitData>(composeRule) {
    fun assertMenuDisplayed(): StatusDropDownMenuRobot {
        composeRule.assertTagDisplayed(STATUS_ITEM_MENU)
        return this
    }

    fun assertMenuNotDisplayed(): StatusDropDownMenuRobot {
        composeRule.assertTagDoesNotExist(STATUS_ITEM_MENU)
        return this
    }

    fun assertBuyStatusDisplayed(): StatusDropDownMenuRobot {
        composeRule.assertTextDisplayed(
            tag = "${STATUS_ITEM}_${StatusType.BUY.name}_TEXT",
            text = "Buy"
        )
        return this
    }

    fun assertRentStatusDisplayed(): StatusDropDownMenuRobot {
        composeRule.assertTextDisplayed(
            tag = "${STATUS_ITEM}_${StatusType.RENT.name}_TEXT",
            text = "Rent"
        )
        return this
    }

    fun assertSoldStatusDisplayed(): StatusDropDownMenuRobot {
        composeRule.assertTextDisplayed(
            tag = "${STATUS_ITEM}_${StatusType.SOLD.name}_TEXT",
            text = "Sold"
        )
        return this
    }

    fun clickBuy(): StatusDropDownMenuRobot {
        composeRule.clickOn("${STATUS_ITEM}_${StatusType.BUY.name}_TEXT")
        return this
    }

    fun clickSold(): StatusDropDownMenuRobot {
        composeRule.clickOn("${STATUS_ITEM}_${StatusType.SOLD.name}_TEXT")
        return this
    }

    override fun setupComponent(data: TestRobotInitData?): StatusDropDownMenuRobot {
        return this
    }

    override fun assertLayoutDisplayed(): StatusDropDownMenuRobot {
        return this
    }
}