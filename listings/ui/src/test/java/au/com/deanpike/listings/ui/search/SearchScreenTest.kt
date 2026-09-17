package au.com.deanpike.listings.ui.search

import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.robot.SearchScreenRobot
import au.com.deanpike.listings.ui.robot.SearchScreenRobotInitData
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchScreenTest : RobolectricTestBase() {
    private val robot = SearchScreenRobot(composeTestRule)

    @Test
    fun `screen should be displayed`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState()
                )
            )
            .assertLayoutDisplayed()
            .assertBuyButtonDisplayed()
            .assertRentButtonDisplayed()
            .assertSoldButtonDisplayed()
            .assertAllDwellingsDisplayed()
            .assertHouseDisplayed()
            .assertTownhouseDisplayed()
            .assertApartmentDisplayed()
            .assertSearchButtonDisplayed()
    }

    @Test
    fun `should display existing location`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(location = "Sydney")
                )
            )
            .assertLayoutDisplayed()
            .assertLocationFieldText("Sydney")
    }

    @Test
    fun `should notify location changed when typing`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState()
                )
            )
            .assertLayoutDisplayed()
            .typeLocation("Melbourne")

        assertEquals(SearchScreenEvent.OnLocationChanged("Melbourne"), robot.lastEvent)
    }

    @Test
    fun `should display location suggestions`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(
                        suggestedLocations = listOf(
                            Location(nameSlug = "sydney-nsw", displayName = "Sydney, NSW")
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .assertLocationSuggestionDisplayed("Sydney, NSW")
    }

    @Test
    fun `should not display location suggestions when none available`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState()
                )
            )
            .assertLayoutDisplayed()
            .assertLocationSuggestionsHidden()
    }

    @Test
    fun `should notify location selected when suggestion clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(
                        suggestedLocations = listOf(
                            Location(nameSlug = "sydney-nsw", displayName = "Sydney, NSW")
                        )
                    )
                )
            )
            .assertLayoutDisplayed()
            .clickLocationSuggestion()

        assertEquals(SearchScreenEvent.OnLocationSelected("Sydney, NSW"), robot.lastEvent)
    }

    @Test
    fun `should notify status changed when buy clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(selectedStatus = StatusType.RENT)
                )
            )
            .assertLayoutDisplayed()
            .clickBuyButton()

        assertEquals(SearchScreenEvent.OnStatusChanged(StatusType.BUY), robot.lastEvent)
    }

    @Test
    fun `should notify status changed when rent clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(selectedStatus = StatusType.BUY)
                )
            )
            .assertLayoutDisplayed()
            .clickRentButton()

        assertEquals(SearchScreenEvent.OnStatusChanged(StatusType.RENT), robot.lastEvent)
    }

    @Test
    fun `should notify status changed when sold clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(selectedStatus = StatusType.BUY)
                )
            )
            .assertLayoutDisplayed()
            .clickSoldButton()

        assertEquals(SearchScreenEvent.OnStatusChanged(StatusType.SOLD), robot.lastEvent)
    }

    @Test
    fun `should notify dwelling types changed when house clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(selectedDwellingTypes = listOf(DwellingType.ALL))
                )
            )
            .assertLayoutDisplayed()
            .clickHouse()

        assertEquals(
            SearchScreenEvent.OnDwellingTypesChanged(listOf(DwellingType.HOUSE)),
            robot.lastEvent
        )
    }

    @Test
    fun `should notify dwelling types changed when townhouse clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(selectedDwellingTypes = listOf(DwellingType.HOUSE))
                )
            )
            .assertLayoutDisplayed()
            .clickTownhouse()

        assertEquals(
            SearchScreenEvent.OnDwellingTypesChanged(listOf(DwellingType.HOUSE, DwellingType.TOWNHOUSE)),
            robot.lastEvent
        )
    }

    @Test
    fun `should notify dwelling types changed when apartment clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState(selectedDwellingTypes = listOf(DwellingType.ALL))
                )
            )
            .assertLayoutDisplayed()
            .clickApartment()

        assertEquals(
            SearchScreenEvent.OnDwellingTypesChanged(listOf(DwellingType.APARTMENT_UNIT_FLAT)),
            robot.lastEvent
        )
    }

    @Test
    fun `should notify search clicked`() {
        robot
            .setupComponent(
                data = SearchScreenRobotInitData(
                    state = SearchScreenState()
                )
            )
            .assertLayoutDisplayed()
            .clickSearchButton()

        assertEquals(SearchScreenEvent.OnSearchClicked, robot.lastEvent)
    }
}
