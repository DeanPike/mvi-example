package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.espresso.Espresso
import au.com.deanpike.detail.ui.property.PropertyDetailScreenContent
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
    var retryClicked = false
        private set

    override fun setupComponent(data: PropertyDetailScreenRobotInitData?): PropertyDetailScreenRobot {
        composeRule.setContent {
            MviExampleTheme {
                PropertyDetailScreenContent(
                    state = data!!.state,
                    loadingAddress = data.loadingAddress,
                    onCloseClicked = {
                        closeClicked = true
                    },
                    onRetryClicked = {
                        retryClicked = true
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): PropertyDetailScreenRobot {
        composeRule.assertTagDisplayed(PROPERTY_DETAILS_LAYOUT)
        return this
    }

    fun assertProgressDisplayed(): PropertyDetailScreenRobot {
        composeRule.assertTagDisplayed(PROPERTY_DETAIL_PROGRESS)
        return this
    }

    fun assertProgressNotDisplayed(): PropertyDetailScreenRobot {
        composeRule.assertTagDoesNotExist(PROPERTY_DETAIL_PROGRESS)
        return this
    }

    fun assertBedroomDisplayed(text: String): PropertyDetailScreenRobot {
        detailItemRobot.assertIconDisplayed(
            testTag = DETAIL_ITEM_BEDROOMS,
            drawable = R.drawable.baseline_bed_24,
            contentDescription = "Number of bedrooms"
        )
        detailItemRobot.assertItemCount(
            testTag = DETAIL_ITEM_BEDROOMS,
            count = text
        )
        return this
    }

    fun assertBathroomDisplayed(text: String): PropertyDetailScreenRobot {
        detailItemRobot.assertIconDisplayed(
            testTag = DETAIL_ITEM_BATHROOMS,
            drawable = R.drawable.baseline_bathtub_24,
            contentDescription = "Number of bathrooms"
        )
        detailItemRobot.assertItemCount(
            testTag = DETAIL_ITEM_BATHROOMS,
            count = text
        )
        return this
    }

    fun assertCarSpaceDisplayed(text: String): PropertyDetailScreenRobot {
        detailItemRobot.assertIconDisplayed(
            testTag = DETAIL_ITEM_CAR_SPACES,
            drawable = R.drawable.baseline_directions_car_24,
            contentDescription = "Number of parking spaces"
        )
        detailItemRobot.assertItemCount(
            testTag = DETAIL_ITEM_CAR_SPACES,
            count = text
        )
        return this
    }

    fun assertHeadlineDisplayed(headline: String): PropertyDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_HEADLINE,
            text = headline
        )
        return this
    }

    fun assertDescriptionDisplayed(): PropertyDetailScreenRobot {
        composeRule.assertTagDisplayed(tag = PROPERTY_DETAIL_DESCRIPTION)
        return this
    }

    fun swipeUp(): PropertyDetailScreenRobot {
        composeRule.swipeUp(PROPERTY_DETAILS_LAYOUT)
        return this
    }

    fun waitForSuccessScreenToBeDisplayed(): PropertyDetailScreenRobot {
        composeRule.waitUntilTagExists(PRICE_COMPONENT_LABEL)
        return this
    }

    fun swipeBack(): PropertyDetailScreenRobot {
        Espresso.pressBack()
        return this
    }

    fun assertLoadingTitleDisplayed(): PropertyDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_LOADING_TITLE,
            text = "Loading data for"
        )
        return this
    }

    fun assertLoadingAddressDisplayed(address: String): PropertyDetailScreenRobot {
        composeRule.assertTextDisplayed(

            tag = PROPERTY_DETAIL_LOADING_ADDRESS,
            text = address
        )
        return this
    }

    fun assertSuccessAddressDisplayed(address: String): PropertyDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_SUCCESS_ADDRESS,
            text = address
        )
        return this
    }

    fun assertBackButtonDisplayed(): PropertyDetailScreenRobot {
        composeRule.assertTagDisplayed(PROPERTY_DETAIL_BACK_BUTTON)
        return this
    }

    fun clickBack(): PropertyDetailScreenRobot {
        composeRule.clickOn(PROPERTY_DETAIL_BACK_BUTTON)
        return this
    }
}

data class PropertyDetailScreenRobotInitData(
    val state: PropertyDetailScreenState,
    val loadingAddress: String
) : TestRobotInitData