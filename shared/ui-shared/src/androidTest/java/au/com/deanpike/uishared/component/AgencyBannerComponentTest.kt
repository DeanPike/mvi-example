package au.com.deanpike.uishared.component

import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.AgencyBannerComponentRobot
import au.com.deanpike.uitestshared.robot.AgencyBannerComponentRobotInitData
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
        val testData = AgencyBannerComponentRobotInitData(
            logo = "https://images.domain.com.au/img/Agencys/17114/logo_17114.png?buster=2024-04-01"
        )
        with(robot) {
            setupComponent(testData)
            waitForIdle()
            assertLayoutDisplayed()
            assertImageDisplayed()
        }
    }

    @Test
    fun should_display_agency_banner_with_no_image() {
        val testData = AgencyBannerComponentRobotInitData(
            logo = null
        )
        robot
            .setupComponent(testData)
            .waitForIdle()
            .assertLayoutDisplayed()
            .assertImageDisplayed()
    }
}