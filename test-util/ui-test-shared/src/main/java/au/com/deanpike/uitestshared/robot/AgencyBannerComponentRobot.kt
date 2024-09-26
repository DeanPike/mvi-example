package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.uishared.component.AgencyBannerComponent
import au.com.deanpike.uishared.component.AgencyBannerTestTags.AGENCY_BANNER_IMAGE
import au.com.deanpike.uishared.component.AgencyBannerTestTags.AGENCY_BANNER_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed

class AgencyBannerComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<AgencyBannerComponentRobot, AgencyBannerComponentRobotInitData>(composeRule) {

    override fun setupComponent(data: AgencyBannerComponentRobotInitData?): AgencyBannerComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                AgencyBannerComponent(
                    agencyColour = "#abcdef",
                    logo = data?.logo
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): AgencyBannerComponentRobot {
        composeRule.assertTagDisplayed(AGENCY_BANNER_LAYOUT)
        return this
    }

    fun assertImageDisplayed(): AgencyBannerComponentRobot {
        composeRule.assertTagDisplayed(AGENCY_BANNER_IMAGE)
        return this
    }

    fun assertImageDisplayedAtPosition(
        parentTag: String,
        position: Int
    ): AgencyBannerComponentRobot {
        composeRule.onNodeWithTag(useUnmergedTree = true, testTag = parentTag)
            .onChildAt(position)
            .onChildren()
            .assertAny(hasTestTag(AGENCY_BANNER_LAYOUT))
        return this
    }
}

data class AgencyBannerComponentRobotInitData(
    val logo: String? = null
) : TestRobotInitData