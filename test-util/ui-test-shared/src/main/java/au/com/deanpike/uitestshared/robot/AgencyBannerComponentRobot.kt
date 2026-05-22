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

class AgencyBannerComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<AgencyBannerComponentRobot, AgencyBannerComponentRobotInitData>(composeRule) {

    override fun setupComponent(data: AgencyBannerComponentRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                AgencyBannerComponent(
                    agencyColour = "#abcdef",
                    logo = data?.logo
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(AGENCY_BANNER_LAYOUT)
    }

    fun assertImageDisplayed() = apply {
        composeRule.assertTagDisplayed(AGENCY_BANNER_IMAGE)
    }

    fun assertImageDisplayedAtPosition(
        parentTag: String,
        position: Int
    ) = apply {
        composeRule.onNodeWithTag(useUnmergedTree = true, testTag = parentTag)
            .onChildAt(position)
            .onChildren()
            .assertAny(hasTestTag(AGENCY_BANNER_LAYOUT))
    }
}

data class AgencyBannerComponentRobotInitData(
    val logo: String? = null
) : TestRobotInitData