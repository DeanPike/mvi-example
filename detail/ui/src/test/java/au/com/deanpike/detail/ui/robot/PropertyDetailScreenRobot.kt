package au.com.deanpike.detail.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.espresso.Espresso
import au.com.deanpike.detail.ui.property.PropertyDetailScreenContent
import au.com.deanpike.detail.ui.property.PropertyDetailScreenEvent
import au.com.deanpike.detail.ui.property.PropertyDetailScreenState
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_BACK_BUTTON
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_LOADING_ADDRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_LOADING_TITLE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_SUCCESS_ADDRESS
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.PriceComponentTestTags.PRICE_COMPONENT_LABEL
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.robot.DetailItemComponentRobot
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.swipeUp
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class PropertyDetailScreenRobot(composeRule: ComposeContentTestRule) : TestRobotBase<PropertyDetailScreenRobot, PropertyDetailScreenRobotInitData>(composeRule) {

    private val detailItemRobot = DetailItemComponentRobot(composeRule)
    var closeClicked = false
        private set
    var event: PropertyDetailScreenEvent? = null

    override fun setupComponent(data: PropertyDetailScreenRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                PropertyDetailScreenContent(
                    state = data!!.state,
                    loadingAddress = data.loadingAddress,
                    onEvent = {
                        event = it
                    },
                    onCloseClicked = {
                        closeClicked = true
                    },
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(PROPERTY_DETAILS_LAYOUT)
    }

    fun assertProgressDisplayed() = apply {
        composeRule.assertTagDisplayed(PROPERTY_DETAIL_PROGRESS)
    }

    fun assertProgressNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(PROPERTY_DETAIL_PROGRESS)
    }

    fun assertBedroomDisplayed(text: String) = apply {
        detailItemRobot.assertIconDisplayed(
            testTag = DETAIL_ITEM_BEDROOMS,
            drawable = R.drawable.baseline_bed_24,
            contentDescription = "Number of bedrooms"
        )
        detailItemRobot.assertItemCount(
            testTag = DETAIL_ITEM_BEDROOMS,
            count = text
        )
    }

    fun assertBathroomDisplayed(text: String) = apply {
        detailItemRobot.assertIconDisplayed(
            testTag = DETAIL_ITEM_BATHROOMS,
            drawable = R.drawable.baseline_bathtub_24,
            contentDescription = "Number of bathrooms"
        )
        detailItemRobot.assertItemCount(
            testTag = DETAIL_ITEM_BATHROOMS,
            count = text
        )
    }

    fun assertCarSpaceDisplayed(text: String) = apply {
        detailItemRobot.assertIconDisplayed(
            testTag = DETAIL_ITEM_CAR_SPACES,
            drawable = R.drawable.baseline_directions_car_24,
            contentDescription = "Number of parking spaces"
        )
        detailItemRobot.assertItemCount(
            testTag = DETAIL_ITEM_CAR_SPACES,
            count = text
        )
    }

    fun assertHeadlineDisplayed(headline: String) = apply {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_HEADLINE,
            text = headline
        )
    }

    fun assertDescriptionDisplayed() = apply {
        composeRule.assertTagDisplayed(tag = PROPERTY_DETAIL_DESCRIPTION)
    }

    fun swipeUp() = apply {
        composeRule.swipeUp(PROPERTY_DETAILS_LAYOUT)
    }

    fun waitForSuccessScreenToBeDisplayed() = apply {
        composeRule.waitUntilTagExists(PRICE_COMPONENT_LABEL)
    }

    fun swipeBack() = apply {
        Espresso.pressBack()
    }

    fun assertLoadingTitleDisplayed() = apply {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_LOADING_TITLE,
            text = "Loading data for"
        )
    }

    fun assertLoadingAddressDisplayed(address: String) = apply {
        composeRule.assertTextDisplayed(

            tag = PROPERTY_DETAIL_LOADING_ADDRESS,
            text = address
        )
    }

    fun assertSuccessAddressDisplayed(address: String) = apply {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_SUCCESS_ADDRESS,
            text = address
        )
    }

    fun assertBackButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(PROPERTY_DETAIL_BACK_BUTTON)
    }

    fun clickBack() = apply {
        composeRule.clickOn(PROPERTY_DETAIL_BACK_BUTTON)
    }
}

data class PropertyDetailScreenRobotInitData(
    val state: PropertyDetailScreenState,
    val loadingAddress: String
) : TestRobotInitData