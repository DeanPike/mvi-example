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

    fun assertBedroomsLayoutDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BEDROOMS)
        return this
    }

    fun assertBedroomLayoutNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BEDROOMS)
        return this
    }

    fun assertNumberOfBedrooms(count: Int): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_BEDROOMS}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_BEDROOMS}_ICON",
            drawable = R.drawable.baseline_bed_24
        )
        return this
    }

    fun assertBathroomsLayoutDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BATHROOMS)
        return this
    }

    fun assertBathroomLayoutNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BATHROOMS)
        return this
    }

    fun assertNumberOfBathrooms(count: Int): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_BATHROOMS}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_BATHROOMS}_ICON",
            drawable = R.drawable.baseline_bathtub_24
        )

        return this
    }

    fun assertParkingLayoutDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_CAR_SPACES)
        return this
    }

    fun assertParkingLayoutNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_CAR_SPACES)
        return this
    }

    fun assertNumberOfParkingSpaces(count: Int): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_CAR_SPACES}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_CAR_SPACES}_ICON",
            drawable = R.drawable.baseline_directions_car_24
        )

        return this
    }

    fun assertDwellingTypeDisplayed(dwellingType: String): PropertyDetailComponentRobot {
        composeRule.assertTextDisplayed(
            tag = DETAIL_ITEM_DWELLING_TYPE,
            text = dwellingType
        )
        return this
    }

    fun assertDwellingTypeNotDisplayed(): PropertyDetailComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_DWELLING_TYPE)
        return this
    }
}

data class PropertyDetailComponentRobotInitData(
    val details: ListingDetails,
    val dwellingType: String?
) : TestRobotInitData