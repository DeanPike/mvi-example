package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.property.PropertyDetailScreenContent
import au.com.deanpike.detail.ui.property.PropertyDetailScreenState
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_ADDRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_CLOSE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PRICE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.robot.DetailItemComponentRobot
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class PropertyDetailScreenRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<PropertyDetailScreenRobot, PropertyDetailScreenRobotInitData>(composeRule) {

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

    fun assertCloseIconDisplayed(): PropertyDetailScreenRobot {
        composeRule.assertTagDisplayed(PROPERTY_DETAIL_CLOSE)
        return this
    }

    fun assertPriceDisplayed(price: String): PropertyDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_PRICE,
            text = price
        )
        return this
    }

    fun assertAddressDisplayed(address: String): PropertyDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_ADDRESS,
            text = address
        )
        return this
    }

    fun assertBedroomDisplayed(text: String): PropertyDetailScreenRobot {
        detailItemRobot.assertIconDisplayed(
            testTag = DETAIL_ITEM_BEDROOMS,
            drawable = R.drawable.bed_outline,
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
            drawable = R.drawable.bath_outline,
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
            drawable = R.drawable.car_outline,
            contentDescription = "Number of parking spaces"
        )
        detailItemRobot.assertItemCount(
            testTag = DETAIL_ITEM_CAR_SPACES,
            count = text
        )
        return this
    }

    fun assertDwellingDisplayed(dwellingType: String): PropertyDetailScreenRobot {
        composeRule.assertTextDisplayed(
            tag = DETAIL_ITEM_DWELLING_TYPE,
            text = dwellingType
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

    fun clickCloseIcon(): PropertyDetailScreenRobot {
        composeRule.clickOn(PROPERTY_DETAIL_CLOSE)
        return this
    }
}

data class PropertyDetailScreenRobotInitData(
    val state: PropertyDetailScreenState
) : TestRobotInitData