package au.com.deanpike.uitestshared.robot

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.commonshared.model.Media
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_IMAGE
import au.com.deanpike.uishared.component.ListingDetailImagesTestTags.LISTING_DETAIL_IMAGES_PAGER
import au.com.deanpike.uishared.component.ListingImagesComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.swipeLeft
import au.com.deanpike.uitestshared.util.swipeRight

class ListingImagesComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<ListingImagesComponentRobot, ListingImagesComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: ListingImagesComponentRobotInitData?): ListingImagesComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                ListingImagesComponent(
                    screenState = data!!.screenStateType,
                    scope = rememberCoroutineScope(),
                    media = data.images
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): ListingImagesComponentRobot {
        composeRule.assertTagDisplayed(LISTING_DETAIL_IMAGES_PAGER)
        return this
    }

    fun assertImage(position: Int): ListingImagesComponentRobot {
        composeRule.assertTagDisplayed(tag = "${LISTING_DETAIL_IMAGES_IMAGE}_$position")
        return this
    }

    fun swipeLeft(): ListingImagesComponentRobot {
        composeRule.swipeLeft(tag = LISTING_DETAIL_IMAGES_PAGER)
        return this
    }

    fun swipeRight(): ListingImagesComponentRobot {
        composeRule.swipeRight(tag = LISTING_DETAIL_IMAGES_PAGER)
        return this
    }
}

data class ListingImagesComponentRobotInitData(
    val images: List<Media>,
    val screenStateType: ScreenStateType
) : TestRobotInitData