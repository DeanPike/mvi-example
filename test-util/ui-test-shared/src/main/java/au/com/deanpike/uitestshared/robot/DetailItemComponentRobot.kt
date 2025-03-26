package au.com.deanpike.uitestshared.robot

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailItemComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertContentDescription
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class DetailItemComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<DetailItemComponentRobot, TestRobotInitData>(composeRule) {

    override fun setupComponent(data: TestRobotInitData?): DetailItemComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                DetailItemComponent(
                    icon = R.drawable.baseline_bed_24,
                    text = "3",
                    description = R.string.number_of_bedrooms,
                    testTag = "DETAIL_ITEM_COMPONENT"
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): DetailItemComponentRobot {
        composeRule.assertTagDisplayed("DETAIL_ITEM_COMPONENT")
        return this
    }

    fun assertIconDisplayed(
        testTag: String,
        @DrawableRes drawable: Int,
        contentDescription: String
    ): DetailItemComponentRobot {
        composeRule.assertDrawableDisplayed(
            tag = "${testTag}_ICON",
            drawable = drawable
        )
        composeRule.assertContentDescription(
            tag = "${testTag}_ICON",
            text = contentDescription
        )
        return this
    }

    fun assertItemCount(
        testTag: String,
        count: String
    ): DetailItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${testTag}_TEXT",
            text = count
        )
        return this
    }
}