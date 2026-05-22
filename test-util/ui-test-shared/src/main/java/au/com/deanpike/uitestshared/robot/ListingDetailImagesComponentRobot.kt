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
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.swipeLeft
import au.com.deanpike.uitestshared.util.swipeRight

class ListingDetailImagesComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ListingDetailImagesComponentRobot, ListingDetailImagesComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: ListingDetailImagesComponentRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                ListingDetailImagesComponent(
                    media = data!!.images
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(LISTING_DETAIL_IMAGES_PAGER)
    }

    fun assertImageIndicator() = apply {
        composeRule.assertTagDisplayed(LISTING_DETAIL_IMAGES_POSITION_INDICATOR)
    }

    fun assertImageIndicatorNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(LISTING_DETAIL_IMAGES_POSITION_INDICATOR)
    }

    fun assertImage(position: Int) = apply {
        composeRule.assertTagDisplayed(
            tag = "${LISTING_DETAIL_IMAGES_IMAGE}_$position"
        )
    }

    fun swipeLeft() = apply {
        composeRule.swipeLeft(tag = LISTING_DETAIL_IMAGES_PAGER)
    }

    fun swipeRight() = apply {
        composeRule.swipeRight(tag = LISTING_DETAIL_IMAGES_PAGER)
    }
}

data class ListingDetailImagesComponentRobotInitData(
    val images: List<Media>
) : TestRobotInitData