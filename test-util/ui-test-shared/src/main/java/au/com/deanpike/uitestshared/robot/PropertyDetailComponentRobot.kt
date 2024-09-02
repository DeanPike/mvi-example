package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_GROUP
import au.com.deanpike.uishared.component.PropertyDetailComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class PropertyDetailComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<PropertyDetailComponentRobot, PropertyDetailComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: PropertyDetailComponentRobotInitData?): PropertyDetailComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                PropertyDetailComponent(
                    details = data!!.details,
                    dwellingType = data.dwellingType

                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_GROUP)
        return this
    }

    fun bedroomsLayoutDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BEDROOMS)
        return this
    }

    fun bedroomLayoutNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BEDROOMS)
        return this
    }

    fun numberOfBedrooms(count: Int): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_BEDROOMS}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_BEDROOMS}_ICON",
            drawable = R.drawable.bed_outline
        )
        return this
    }

    fun bathroomsLayoutDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BATHROOMS)
        return this
    }

    fun bathroomLayoutNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BATHROOMS)
        return this
    }

    fun numberOfBathrooms(count: Int): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_BATHROOMS}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_BATHROOMS}_ICON",
            drawable = R.drawable.bath_outline
        )

        return this
    }

    fun parkingLayoutDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_CAR_SPACES)
        return this
    }

    fun parkingLayoutNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_CAR_SPACES)
        return this
    }

    fun numberOfParkingSpaces(count: Int): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_CAR_SPACES}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_CAR_SPACES}_ICON",
            drawable = R.drawable.car_outline
        )

        return this
    }

    fun dwellingTypeDisplayed(dwellingType: String): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = DETAIL_ITEM_DWELLING_TYPE,
            text = dwellingType
        )
        return this
    }

    fun dwellingTypeNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_DWELLING_TYPE)
        return this
    }
}

data class PropertyDetailComponentRobotInitData(
    val details: ListingDetails,
    val dwellingType: String?
) : TestRobotInitData