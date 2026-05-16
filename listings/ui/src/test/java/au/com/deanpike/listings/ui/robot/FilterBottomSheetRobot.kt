package au.com.deanpike.listings.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.list.component.FilterBottomSheet
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_ALL_DWELLINGS
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_APARTMENT
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_APPLY
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_BUY_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_HOUSE
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_LAYOUT
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_RENT_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_SOLD_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterBottomSheetTestTags.FILTER_BOTTOM_SHEET_TOWNHOUSE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertIsOff
import au.com.deanpike.uitestshared.util.assertIsOn
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.disableAnimations

class FilterBottomSheetRobot(composeRule: ComposeContentTestRule) : TestRobotBase<FilterBottomSheetRobot, FilterBottomSheetRobotInitData>(composeRule) {
    var selectedStatusType: StatusType? = null
        private set
    var selectedDwellingTypes: List<DwellingType>? = null
        private set

    override fun setupComponent(data: FilterBottomSheetRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                FilterBottomSheet(
                    statusType = data!!.statusType,
                    dwellingTypes = data.dwellingTypes,
                    onApply = { statusType, dwellingTypes ->
                        selectedStatusType = statusType
                        selectedDwellingTypes = dwellingTypes
                    }
                )
            }
        }
        composeRule.disableAnimations()
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayed(FILTER_BOTTOM_SHEET_LAYOUT)
    }

    fun assertLayoutHidden() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDoesNotExist(FILTER_BOTTOM_SHEET_LAYOUT)
    }

    fun assertBuyButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(FILTER_BOTTOM_SHEET_BUY_BUTTON)
    }

    fun assertRentButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(FILTER_BOTTOM_SHEET_RENT_BUTTON)
    }

    fun assertSoldButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(FILTER_BOTTOM_SHEET_SOLD_BUTTON)
    }

    fun assertAllPropertyTypesDisplayed() = apply {
        composeRule.assertTextDisplayed(
            tag = "${FILTER_BOTTOM_SHEET_ALL_DWELLINGS}_TEXT",
            text = "All property types"
        )
        composeRule.assertTagDisplayed("${FILTER_BOTTOM_SHEET_ALL_DWELLINGS}_CHECKBOX")
    }

    fun assertHousePropertyTypeDisplayed() = apply {
        composeRule.assertTextDisplayed(
            tag = "${FILTER_BOTTOM_SHEET_HOUSE}_TEXT",
            text = "House"
        )
        composeRule.assertTagDisplayed("${FILTER_BOTTOM_SHEET_HOUSE}_CHECKBOX")
    }

    fun assertTownhousePropertyTypeDisplayed() = apply {
        composeRule.assertTextDisplayed(
            tag = "${FILTER_BOTTOM_SHEET_TOWNHOUSE}_TEXT",
            text = "Townhouse"
        )
        composeRule.assertTagDisplayed("${FILTER_BOTTOM_SHEET_TOWNHOUSE}_CHECKBOX")
    }

    fun assertApartmentPropertyTypeDisplayed() = apply {
        composeRule.assertTextDisplayed(
            tag = "${FILTER_BOTTOM_SHEET_APARTMENT}_TEXT",
            text = "Apartment / Unit / Flat"
        )
        composeRule.assertTagDisplayed("${FILTER_BOTTOM_SHEET_APARTMENT}_CHECKBOX")
    }

    fun assertApplyButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(FILTER_BOTTOM_SHEET_APPLY)
        composeRule.assertTextDisplayed(tag = "${FILTER_BOTTOM_SHEET_APPLY}_TEXT", text = "Apply")
    }

    fun clickBuyButton() = apply {
        composeRule.clickOn(FILTER_BOTTOM_SHEET_BUY_BUTTON)
    }

    fun clickRentButton() = apply {
        composeRule.clickOn(FILTER_BOTTOM_SHEET_RENT_BUTTON)
    }

    fun clickSoldButton() = apply {
        composeRule.clickOn(FILTER_BOTTOM_SHEET_SOLD_BUTTON)
    }

    fun clickApplyButton() = apply {
        composeRule.clickOn(FILTER_BOTTOM_SHEET_APPLY)
        composeRule.advanceTimeAndWait()
    }

    fun assertAllDwellingTypeSelected(isSelected: Boolean) = apply {
        if (isSelected) {
            composeRule.assertIsOn("${FILTER_BOTTOM_SHEET_ALL_DWELLINGS}_CHECKBOX")
        } else {
            composeRule.assertIsOff("${FILTER_BOTTOM_SHEET_ALL_DWELLINGS}_CHECKBOX")
        }
    }

    fun assertHouseDwellingTypeSelected(isSelected: Boolean) = apply {
        if (isSelected) {
            composeRule.assertIsOn("${FILTER_BOTTOM_SHEET_HOUSE}_CHECKBOX")
        } else {
            composeRule.assertIsOff("${FILTER_BOTTOM_SHEET_HOUSE}_CHECKBOX")
        }
    }

    fun assertTownhouseDwellingTypeSelected(isSelected: Boolean) = apply {
        if (isSelected) {
            composeRule.assertIsOn("${FILTER_BOTTOM_SHEET_TOWNHOUSE}_CHECKBOX")
        } else {
            composeRule.assertIsOff("${FILTER_BOTTOM_SHEET_TOWNHOUSE}_CHECKBOX")
        }
    }

    fun assertApartmentDwellingTypeSelected(isSelected: Boolean) = apply {
        if (isSelected) {
            composeRule.assertIsOn("${FILTER_BOTTOM_SHEET_APARTMENT}_CHECKBOX")
        } else {
            composeRule.assertIsOff("${FILTER_BOTTOM_SHEET_APARTMENT}_CHECKBOX")
        }
    }

    fun clickAllDwellingType() = apply {
        composeRule.clickOn("${FILTER_BOTTOM_SHEET_ALL_DWELLINGS}_CHECKBOX")
        composeRule.advanceTimeAndWait()
    }

    fun clickHouseDwellingType() = apply {
        composeRule.clickOn("${FILTER_BOTTOM_SHEET_HOUSE}_CHECKBOX")
        composeRule.advanceTimeAndWait()
    }

    fun clickTownhouseDwellingType() = apply {
        composeRule.clickOn("${FILTER_BOTTOM_SHEET_TOWNHOUSE}_CHECKBOX")
        composeRule.advanceTimeAndWait()
    }

    fun clickApartmentDwellingType() = apply {
        composeRule.clickOn("${FILTER_BOTTOM_SHEET_APARTMENT}_CHECKBOX")
        composeRule.advanceTimeAndWait()
    }
}

data class FilterBottomSheetRobotInitData(
    val statusType: StatusType,
    val dwellingTypes: List<DwellingType>,
) : TestRobotInitData
