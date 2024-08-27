package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.component.AgencyBannerComponent
import au.com.deanpike.uishared.component.AgencyBannerTestTags.AGENCY_BANNER_IMAGE
import au.com.deanpike.uishared.component.AgencyBannerTestTags.AGENCY_BANNER_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.util.assertTagDisplayed

class AgencyBannerComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<AgencyBannerComponentRobot>(composeRule) {

    fun setupComponent(logo: String? = null): AgencyBannerComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                AgencyBannerComponent(
                    agencyColour = "#abcdef",
                    logo = logo
                )
            }
        }
        return this
    }

    fun assertLayoutDisplayed(): AgencyBannerComponentRobot {
        composeRule.assertTagDisplayed(AGENCY_BANNER_LAYOUT)
        return this
    }

    fun assertImageDisplayed(): AgencyBannerComponentRobot {
        composeRule.assertTagDisplayed(AGENCY_BANNER_IMAGE)
        return this
    }
}