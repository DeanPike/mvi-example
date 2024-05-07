package au.com.deanpike.ui.unit.screen.list.component

import au.com.deanpike.client.type.ListingType
import au.com.deanpike.client.type.StatusType
import au.com.deanpike.ui.framework.ability.list.component.FilterComponentAbility
import au.com.deanpike.ui.screen.list.component.FilterComponent
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FilterComponentTest : UiUnitTestBase() {

    val ability = FilterComponentAbility(composeTestRule)

    @Test
    fun should_show_filter_component() {
        var statusSelected = StatusType.RENT
        var propertyTypeSelected = false
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    FilterComponent(
                        selectedStatus = StatusType.RENT,
                        selectedListingTypes = listOf(ListingType.HOUSE),
                        onStatusSelected = {
                            statusSelected = it
                        },
                        onListingTypeSelected = {
                            propertyTypeSelected = true
                        }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability) {
            assertStatusButtonDisplayed("Rent")
            clickStatusButton()
            composeTestRule.advanceTimeAndWait()

            assertBuyStatusItemDisplayed()
            assertRentStatusItemDisplayed()
            assertSoldStatusItemDisplayed()

            clickBuyStatus()
            composeTestRule.advanceTimeAndWait()

            assertThat(statusSelected).isEqualTo(StatusType.BUY)

            assertListingTypeButtonDisplayed(text = "1 Property type")
            clickListingTypeButton()
            composeTestRule.advanceTimeAndWait()
            assertThat(propertyTypeSelected).isTrue()
        }
    }

    @Test
    fun should_show_filter_component_with_no_listing_types() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    FilterComponent(
                        selectedStatus = StatusType.RENT,
                        selectedListingTypes = emptyList(),
                        onStatusSelected = { },
                        onListingTypeSelected = { }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability){
            assertListingTypeButtonDisplayed("Property types")
        }
    }

    @Test
    fun should_show_filter_component_with_multiple_listing_types() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    FilterComponent(
                        selectedStatus = StatusType.RENT,
                        selectedListingTypes = listOf(ListingType.HOUSE, ListingType.TOWNHOUSE),
                        onStatusSelected = { },
                        onListingTypeSelected = { }
                    )
                }
            }
            advanceTimeAndWait()
        }

        with(ability){
            assertListingTypeButtonDisplayed("2 Property types")
        }
    }
}