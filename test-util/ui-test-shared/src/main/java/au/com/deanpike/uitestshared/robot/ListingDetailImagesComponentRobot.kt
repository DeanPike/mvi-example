package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.uishared.component.ListingDetailImagesComponent
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_IMAGE
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_PAGER
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_POSITION_INDICATOR
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.swipeLeft
import au.com.deanpike.uitestshared.util.swipeRight

class ListingDetailImagesComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ListingDetailImagesComponentRobot, ListingDetailImagesComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: ListingDetailImagesComponentRobotInitData?): ListingDetailImagesComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                ListingDetailImagesComponent(
                    media = data!!.images
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): ListingDetailImagesComponentRobot {
        composeRule.assertTagDisplayed(LISTING_DETAIL_IMAGES_PAGER)
        return this
    }

    fun assertImageIndicator(): ListingDetailImagesComponentRobot {
        composeRule.assertTagDisplayed(LISTING_DETAIL_IMAGES_POSITION_INDICATOR)
        return this
    }

    fun assertImage(position: Int): ListingDetailImagesComponentRobot {
        composeRule.assertTagDisplayed(
            tag = "${LISTING_DETAIL_IMAGES_IMAGE}_$position"
        )
        return this
    }

    fun swipeLeft(): ListingDetailImagesComponentRobot {
        composeRule.swipeLeft(tag = LISTING_DETAIL_IMAGES_PAGER)
        return this
    }

    fun swipeRight(): ListingDetailImagesComponentRobot {
        composeRule.swipeRight(tag = LISTING_DETAIL_IMAGES_PAGER)
        return this
    }
}

data class ListingDetailImagesComponentRobotInitData(
    val images: List<Media>
) : TestRobotInitData