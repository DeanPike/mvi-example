package au.com.deanpike.ui.unit.screen.listingType

import au.com.deanpike.client.type.ListingType
import au.com.deanpike.ui.framework.ability.listingType.ListingTypeScreenAbility
import au.com.deanpike.ui.screen.listingType.ListingTypeScreenContent
import au.com.deanpike.ui.screen.listingType.ListingTypeState
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ListingTypeScreenTest : UiUnitTestBase() {
    private val ability = ListingTypeScreenAbility(composeTestRule)

    @Test
    fun should_show_default_screen() {
        var listingTypeSelected: ListingType = ListingType.ALL
        var isSelected = false
        var applyClicked = false
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingTypeScreenContent(
                        state = ListingTypeState(),
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
            advanceTimeAndWait()
        }

        with(ability) {
            assertListingTypeScreenDisplayed()

            assertAllDisplayed()
            assertAllSelected(true)

            assertHouseDisplayed()
            assertHouseSelected(false)

            assertTownhouseDisplayed()
            assertTownhouseSelected(false)

            assertApartmentDisplayed()
            assertApartmentSelected(false)

            assertApplyDisplayed()

            clickHouse()
            composeTestRule.advanceTimeAndWait()

            assertThat(listingTypeSelected).isEqualTo(ListingType.HOUSE)
            assertThat(isSelected).isTrue()

            clickApply()
            composeTestRule.advanceTimeAndWait()
            assertThat(applyClicked).isTrue()

        }
    }
}