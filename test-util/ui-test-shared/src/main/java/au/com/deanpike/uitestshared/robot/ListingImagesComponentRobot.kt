package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_IMAGE
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_PAGER
import au.com.deanpike.uishared.component.ListingImagesComponent
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.swipeLeft
import au.com.deanpike.uitestshared.util.swipeRight

class ListingImagesComponentRobot(composeRule: ComposeContentTestRule) :
    TestRobotBase<ListingImagesComponentRobot, ListingImagesComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: ListingImagesComponentRobotInitData?) = apply {
        composeRule.setContent {
            AppTheme {
                ListingImagesComponent(
                    screenState = data!!.screenStateType,
                    media = data.images
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(LISTING_DETAIL_IMAGES_PAGER)
    }

    fun assertImage(position: Int) = apply {
        composeRule.assertTagDisplayed(tag = "${LISTING_DETAIL_IMAGES_IMAGE}_$position")
    }

    fun swipeLeft() = apply {
        composeRule.swipeLeft(tag = LISTING_DETAIL_IMAGES_PAGER)
    }

    fun swipeRight() = apply {
        composeRule.swipeRight(tag = LISTING_DETAIL_IMAGES_PAGER)
    }
}

data class ListingImagesComponentRobotInitData(
    val images: List<Media>,
    val screenStateType: ScreenStateType
) : TestRobotInitData