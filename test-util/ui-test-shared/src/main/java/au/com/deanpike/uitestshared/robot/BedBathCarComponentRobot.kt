package au.com.deanpike.uitestshared.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.BedBathCarComponent
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_GROUP
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class BedBathCarComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<BedBathCarComponentRobot, BedBathCarComponentRobotInitData>(composeRule) {
    override fun setupComponent(data: BedBathCarComponentRobotInitData?): BedBathCarComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                BedBathCarComponent(
                    bedrooms = data!!.bedrooms,
                    bathrooms = data!!.bathrooms,
                    carSpaces = data!!.carSpaces
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): BedBathCarComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_GROUP)
        return this
    }

    fun assertBedroomsLayoutDisplayed(): BedBathCarComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BEDROOMS)
        return this
    }

    fun assertBedroomLayoutNotDisplayed(): BedBathCarComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BEDROOMS)
        return this
    }

    fun assertNumberOfBedrooms(count: Int): BedBathCarComponentRobot {
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

    fun assertBathroomsLayoutDisplayed(): BedBathCarComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_BATHROOMS)
        return this
    }

    fun assertBathroomLayoutNotDisplayed(): BedBathCarComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_BATHROOMS)
        return this
    }

    fun assertNumberOfBathrooms(count: Int): BedBathCarComponentRobot {
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

    fun assertCarSpaceLayoutDisplayed(): BedBathCarComponentRobot {
        composeRule.assertTagDisplayed(DETAIL_ITEM_CAR_SPACES)
        return this
    }

    fun assertCarSpaceLayoutNotDisplayed(): BedBathCarComponentRobot {
        composeRule.assertTagDoesNotExist(DETAIL_ITEM_CAR_SPACES)
        return this
    }

    fun assertNumberOfCarSpaces(count: Int): BedBathCarComponentRobot {
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
}

data class BedBathCarComponentRobotInitData(
    val bedrooms: Int? = null,
    val bathrooms: Int? = null,
    val carSpaces: Int? = null
) : TestRobotInitData