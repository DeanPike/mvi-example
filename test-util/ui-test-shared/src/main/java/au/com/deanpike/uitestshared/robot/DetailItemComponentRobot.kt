package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailItemComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class DetailItemComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<DetailItemComponentRobot>(composeRule) {
    private val testTag = "BEDROOM_ITEM"

    fun setupComponent(
    ): DetailItemComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                DetailItemComponent(
                    icon = R.drawable.bed_outline,
                    text = "3",
                    description = R.string.number_of_bedrooms,
                    testTag = testTag
                )
            }
        }
        return this
    }

    fun assertLayoutDisplayed(): DetailItemComponentRobot {
        composeRule.assertTagDisplayed(testTag)
        return this
    }

    fun assertIconDisplayed(): DetailItemComponentRobot {
        composeRule.assertDrawableDisplayed(
            tag = "${testTag}_ICON",
            drawable = R.drawable.bed_outline
        )
        return this
    }

    fun assertItemCount(): DetailItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${testTag}_TEXT",
            text = "3"
        )
        return this
    }
}