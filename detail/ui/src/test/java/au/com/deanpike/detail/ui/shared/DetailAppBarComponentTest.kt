package au.com.deanpike.detail.ui.shared

import au.com.deanpike.detail.ui.robot.DetailAppBarComponentRobot
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DetailAppBarComponentTest : RobolectricTestBase() {
    private val robot = DetailAppBarComponentRobot(composeTestRule)

    @Test
    fun should_display_detail_app_bar() {
        robot
            .setupComponent()
            .assertLayoutDisplayed()
            .assertCloseIconDisplayed()
            .clickOnClose()

        assertThat(robot.closeClicked).isTrue()
    }
}