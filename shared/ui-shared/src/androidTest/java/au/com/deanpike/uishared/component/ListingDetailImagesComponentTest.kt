package au.com.deanpike.uishared.component

import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.ListingDetailImagesComponentRobot
import au.com.deanpike.uitestshared.robot.ListingDetailImagesComponentRobotInitData
import org.junit.Before
import org.junit.Test

class ListingDetailImagesComponentTest : UiUnitTestBase() {
    private lateinit var robot: ListingDetailImagesComponentRobot

    @Before
    fun setupTest() {
        // Disable animations
        composeTestRule.mainClock.autoAdvance = false
        robot = ListingDetailImagesComponentRobot(composeTestRule)
    }

    @Test
    fun should_display_images_component() {
        robot
            .setupComponent(
                data = ListingDetailImagesComponentRobotInitData(
                    images = listOf(
                        Media(
                            mediaType = MediaType.PHOTO,
                            url = "https://bucket-api.domain.com.au/v1/bucket/image/2019096805_1_1_240305_054335-w2048-h1365"
                        ),
                        Media(
                            mediaType = MediaType.PHOTO,
                            url = "https://bucket-api.domain.com.au/v1/bucket/image/2019096805_2_1_240305_054335-w2048-h1365"
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertImageIndicator()
            .assertImage(position = 0)
            .swipeLeft()
            .assertImage(position = 1)
            .swipeRight()
            .assertImage(position = 0)
    }
}