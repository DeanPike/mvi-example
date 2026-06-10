package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.BedBathCarComponent
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_GROUP
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class BedBathCarComponentRobot(composeRule: ComposeContentTestRule) :
    TestRobotBase<BedBathCarComponentRobot, BedBathCarComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: BedBathCarComponentRobotInitData?) = apply {
        composeRule.setContent {
            AppTheme {
                BedBathCarComponent(
                    bedrooms = data!!.bedrooms,
                    bathrooms = data.bathrooms,
                    carSpaces = data.carSpaces
                )
            }
        }
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(DETAIL_ITEM_GROUP)
    }

    fun assertBedroomsLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BEDROOMS)
    }

    fun assertBedroomLayoutNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BEDROOMS)
    }

    fun assertNumberOfBedrooms(count: Int) = apply {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_BEDROOMS}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_BEDROOMS}_ICON",
            drawable = R.drawable.baseline_bed_24
        )
    }

    fun assertBathroomsLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BATHROOMS)
    }

    fun assertBathroomLayoutNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BATHROOMS)
    }

    fun assertNumberOfBathrooms(count: Int) = apply {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_BATHROOMS}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_BATHROOMS}_ICON",
            drawable = R.drawable.baseline_bathtub_24
        )
    }

    fun assertCarSpaceLayoutDisplayed() = apply {
        composeRule.assertTagDisplayed(DETAIL_ITEM_CAR_SPACES)
    }

    fun assertCarSpaceLayoutNotDisplayed() = apply {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_CAR_SPACES)
    }

    fun assertNumberOfCarSpaces(count: Int) = apply {
        composeRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_CAR_SPACES}_TEXT",
            text = "$count"
        )
        composeRule.assertDrawableDisplayed(
            tag = "${DETAIL_ITEM_CAR_SPACES}_ICON",
            drawable = R.drawable.baseline_directions_car_24
        )
    }
}

data class BedBathCarComponentRobotInitData(
    val bedrooms: Int? = null,
    val bathrooms: Int? = null,
    val carSpaces: Int? = null
) : TestRobotInitData