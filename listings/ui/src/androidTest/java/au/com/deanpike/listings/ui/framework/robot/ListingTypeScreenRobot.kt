package au.com.deanpike.listings.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenContent
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_ALL
import au.com.deanpike.listings.ui.listingType.ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_LAYOUT
import au.com.deanpike.listings.ui.listingType.ListingTypeState
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertButtonWithTextDisplayed
import au.com.deanpike.uitestshared.util.assertIsOff
import au.com.deanpike.uitestshared.util.assertIsOn
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class ListingTypeScreenRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ListingTypeScreenRobot, ListingTypeScreenRobotInitData>(composeRule) {
    var listingTypeSelected: DwellingType = DwellingType.ALL
        private set
    var isSelected = false
        private set
    var applyClicked = false
        private set

    override fun setupComponent(data: ListingTypeScreenRobotInitData?): ListingTypeScreenRobot {
        composeRule.setContent {
            MviExampleTheme {
                ListingTypeScreenContent(
                    state = data!!.state,
                    onItemSelected = { type, selected ->
                        listingTypeSelected = type
                        isSelected = selected
                    },
                    onApplyClicked = {
                        applyClicked = true
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): ListingTypeScreenRobot {
        composeRule.assertTagDisplayed(LISTING_TYPE_SCREEN_LAYOUT)
        return this
    }

    fun assertAllDisplayed(): ListingTypeScreenRobot {
        composeRule.assertTextDisplayed(tag = "${LISTING_TYPE_SCREEN_ALL}_TEXT", text = "All")
        return this
    }

    fun assertAllSelected(isSelected: Boolean): ListingTypeScreenRobot {
        if (isSelected) {
            composeRule.assertIsOn(tag = "${LISTING_TYPE_SCREEN_ALL}_CHECKBOX")
        } else {
            composeRule.assertIsOn(tag = "${LISTING_TYPE_SCREEN_ALL}_CHECKBOX")
        }
        return this
    }

    fun clickAll(): ListingTypeScreenRobot {
        composeRule.clickOn("${LISTING_TYPE_SCREEN_ALL}_CHECKBOX")
        return this
    }

    // House
    fun assertHouseDisplayed(): ListingTypeScreenRobot {
        composeRule.assertTextDisplayed(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_TEXT", text = "House")
        return this
    }

    fun assertHouseSelected(isSelected: Boolean): ListingTypeScreenRobot {
        if (isSelected) {
            composeRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_CHECKBOX")
        } else {
            composeRule.assertIsOff(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_CHECKBOX")
        }
        return this
    }

    fun clickHouse(): ListingTypeScreenRobot {
        composeRule.clickOn("${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_HOUSE}_CHECKBOX")
        return this
    }

    // TownHouse
    fun assertTownhouseDisplayed(): ListingTypeScreenRobot {
        composeRule.assertTextDisplayed(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_TEXT", text = "Townhouse")
        return this
    }

    fun assertTownhouseSelected(isSelected: Boolean): ListingTypeScreenRobot {
        if (isSelected) {
            composeRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_CHECKBOX")
        } else {
            composeRule.assertIsOff(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_CHECKBOX")
        }
        return this
    }

    fun clickTownhouse(): ListingTypeScreenRobot {
        composeRule.clickOn("${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_TOWNHOUSE}_CHECKBOX")
        return this
    }

    // Apartment
    fun assertApartmentDisplayed(): ListingTypeScreenRobot {
        composeRule.assertTextDisplayed(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_TEXT", text = "Apartment / Unit / Flat")
        return this
    }

    fun assertApartmentSelected(isSelected: Boolean): ListingTypeScreenRobot {
        if (isSelected) {
            composeRule.assertIsOn(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_CHECKBOX")
        } else {
            composeRule.assertIsOff(tag = "${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_CHECKBOX")
        }
        return this
    }

    fun clickApartment(): ListingTypeScreenRobot {
        composeRule.clickOn("${ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APARTMENT}_CHECKBOX")
        return this
    }

    // Button
    fun assertApplyDisplayed(): ListingTypeScreenRobot {
        composeRule.assertButtonWithTextDisplayed(tag = ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APPLY, text = "Apply")
        return this
    }

    fun clickApply(): ListingTypeScreenRobot {
        composeRule.clickOn(tag = ListingTypeScreenTestTags.LISTING_TYPE_SCREEN_APPLY)
        return this
    }
}

data class ListingTypeScreenRobotInitData(
    val state: ListingTypeState
) : TestRobotInitData