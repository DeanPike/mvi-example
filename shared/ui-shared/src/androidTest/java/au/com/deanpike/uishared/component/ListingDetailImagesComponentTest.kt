package au.com.deanpike.uishared.component

import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.robot.ListingDetailImagesComponentRobot
import au.com.deanpike.uitestshared.robot.ListingDetailImagesComponentRobotInitData
import au.com.deanpike.uitestshared.robot.ListingImagesComponentRobot
import au.com.deanpike.uitestshared.robot.ListingImagesComponentRobotInitData
import au.com.deanpike.uitestshared.util.disableAnimations
import org.junit.Before
import org.junit.Test

class ListingDetailImagesComponentTest : UiUnitTestBase() {
    private val robot = ListingDetailImagesComponentRobot(composeTestRule)
    private val listingImagesRobot = ListingImagesComponentRobot(composeTestRule)

    @Before
    fun setupTest() {
        composeTestRule.disableAnimations()
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

    @Test
    fun should_show_listing_images() {
        listingImagesRobot
            .setupComponent(
                data = ListingImagesComponentRobotInitData(
                    screenStateType = ScreenStateType.LOADING,
                    images = listOf(
                        Media(
                            mediaType = MediaType.PHOTO,
                            url = "https://bucket-api.domain.com.au/v1/bucket/image/2019096805_1_1_240305_054335-w2048-h1365"
                        ),
                        Media(
                            mediaType = MediaType.PHOTO,
                            url = "https://bucket-api.domain.com.au/v1/bucket/image/2019096805_2_1_240305_054335-w2048-h1365"
                        ),
                        Media(
                            mediaType = MediaType.PHOTO,
                            url = "https://bucket-api.domain.com.au/v1/bucket/image/2019096805_2_1_240305_054335-w2048-h1365"
                        )
                    )
                )
            )
            .waitForIdle()
            .assertLayoutDisplayed()
            .assertImage(position = 0)
            .swipeLeft()
            .assertImage(position = 1)
            .swipeLeft()
            .assertImage(position = 2)
            .swipeRight()
            .assertImage(position = 1)
    }
}