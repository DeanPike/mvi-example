package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.AgencyBannerComponentRobot
import org.junit.Before
import org.junit.Test

class AgencyBannerComponentTest : UiUnitTestBase() {

    private lateinit var robot: AgencyBannerComponentRobot

    @Before
    fun setupTest() {
        robot = AgencyBannerComponentRobot(composeTestRule)
    }

    @Test
    fun should_display_agency_banner() {
        with(robot) {
            setupComponent(logo = "https://images.domain.com.au/img/Agencys/17114/logo_17114.png?buster=2024-04-01")
            waitForIdle()
            assertLayoutDisplayed()
            assertImageDisplayed()
        }
    }

    @Test
    fun should_display_agency_banner_with_no_image() {
        robot
            .setupComponent(logo = null)
            .waitForIdle()
            .assertLayoutDisplayed()
            .assertImageDisplayed()
    }
}