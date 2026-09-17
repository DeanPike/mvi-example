package au.com.deanpike.listings.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.search.SearchScreenContent
import au.com.deanpike.listings.ui.search.SearchScreenEvent
import au.com.deanpike.listings.ui.search.SearchScreenState
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_ALL_DWELLINGS
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_APARTMENT
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_BUY_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_HOUSE
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_LOCATION_FIELD
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_LOCATION_SUGGESTION
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_RENT_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_SEARCH_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_SOLD_BUTTON
import au.com.deanpike.listings.ui.search.SearchScreenTestTags.SEARCH_SCREEN_TOWNHOUSE
import au.com.deanpike.uishared.theme.AppTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.disableAnimations
import au.com.deanpike.uitestshared.util.writeText

class SearchScreenRobot(composeRule: ComposeContentTestRule) : TestRobotBase<SearchScreenRobot, SearchScreenRobotInitData>(composeRule) {
    var lastEvent: SearchScreenEvent? = null
        private set

    override fun setupComponent(data: SearchScreenRobotInitData?) = apply {
        composeRule.setContent {
            AppTheme {
                SearchScreenContent(
                    state = data?.state ?: SearchScreenState(),
                    onEvent = {
                        lastEvent = it
                    }
                )
            }
        }
        composeRule.disableAnimations()
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayed(SEARCH_SCREEN_LOCATION_FIELD)
    }

    fun assertLocationFieldText(text: String) = apply {
        composeRule.assertTextDisplayed(tag = SEARCH_SCREEN_LOCATION_FIELD, text = text)
    }

    fun typeLocation(text: String) = apply {
        composeRule.writeText(tag = SEARCH_SCREEN_LOCATION_FIELD, text = text)
        composeRule.advanceTimeAndWait()
    }

    fun assertLocationSuggestionDisplayed(text: String) = apply {
        composeRule.assertTextDisplayed(text = text)
    }

    fun clickLocationSuggestion() = apply {
        composeRule.clickOn(SEARCH_SCREEN_LOCATION_SUGGESTION)
    }

    fun assertLocationSuggestionsHidden() = apply {
        composeRule.assertTagDoesNotExist(SEARCH_SCREEN_LOCATION_SUGGESTION)
    }

    fun assertBuyButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_BUY_BUTTON)
    }

    fun assertRentButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_RENT_BUTTON)
    }

    fun assertSoldButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_SOLD_BUTTON)
    }

    fun clickBuyButton() = apply {
        composeRule.clickOn(SEARCH_SCREEN_BUY_BUTTON)
    }

    fun clickRentButton() = apply {
        composeRule.clickOn(SEARCH_SCREEN_RENT_BUTTON)
    }

    fun clickSoldButton() = apply {
        composeRule.clickOn(SEARCH_SCREEN_SOLD_BUTTON)
    }

    fun assertAllDwellingsDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_ALL_DWELLINGS)
    }

    fun assertHouseDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_HOUSE)
    }

    fun assertTownhouseDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_TOWNHOUSE)
    }

    fun assertApartmentDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_APARTMENT)
    }

    fun clickHouse() = apply {
        composeRule.clickOn("${SEARCH_SCREEN_HOUSE}_CHECKBOX")
    }

    fun clickTownhouse() = apply {
        composeRule.clickOn("${SEARCH_SCREEN_TOWNHOUSE}_CHECKBOX")
    }

    fun clickApartment() = apply {
        composeRule.clickOn("${SEARCH_SCREEN_APARTMENT}_CHECKBOX")
    }

    fun assertSearchButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(SEARCH_SCREEN_SEARCH_BUTTON)
    }

    fun clickSearchButton() = apply {
        composeRule.clickOn(SEARCH_SCREEN_SEARCH_BUTTON)
    }
}

data class SearchScreenRobotInitData(
    val state: SearchScreenState
) : TestRobotInitData
