package au.com.deanpike.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.component.FilterComponent
import au.com.deanpike.ui.screen.list.component.FilterComponentTestTags.FILTER_COMPONENT_LAYOUT
import au.com.deanpike.ui.screen.list.component.FilterComponentTestTags.LISTING_TYPE
import au.com.deanpike.ui.screen.list.component.FilterComponentTestTags.STATUS_BUTTON
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import org.assertj.core.api.Assertions.assertThat

class FilterComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<FilterComponentRobot>(composeRule) {
    private var statusSelected = StatusType.RENT
    private var listingTypeSelected = false

    fun setUpLoginScreen(
        status: StatusType = StatusType.RENT,
        listingTypes: List<DwellingType> = listOf(DwellingType.HOUSE)
    ): FilterComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                FilterComponent(
                    selectedStatus = status,
                    selectedListingTypes = listingTypes,
                    onStatusSelected = {
                        statusSelected = it
                    },
                    onListingTypeSelected = {
                        listingTypeSelected = true
                    }
                )

            }
        }
        return this
    }

    fun assertLayoutDisplayed(): FilterComponentRobot {
        composeRule.assertTagDisplayed(FILTER_COMPONENT_LAYOUT)
        return this
    }

    fun assertStatusButtonText(text: String): FilterComponentRobot {
        composeRule.assertTextDisplayed(tag = "${STATUS_BUTTON}_TEXT", text = text)
        return this
    }

    fun assertStatusButtonIcon(): FilterComponentRobot {
        composeRule.assertDrawableDisplayed(
            tag = "${STATUS_BUTTON}_ICON",
            drawable = R.drawable.arrow_drop_down
        )
        return this
    }

    fun selectStatusButton(): FilterComponentRobot {
        composeRule.clickOn(STATUS_BUTTON)
        return this
    }

    fun assertListingTypeButtonText(text: String): FilterComponentRobot {
        composeRule.assertTextDisplayed(tag = "${LISTING_TYPE}_TEXT", text = text)
        return this
    }

    fun clickListingTypeButton(): FilterComponentRobot {
        composeRule.clickOn(LISTING_TYPE)
        return this
    }

    fun assertStatusSelected(status: StatusType) {
        assertThat(statusSelected).isEqualTo(status)
    }

    fun assertListingTypeButtonClicked() {
        assertThat(listingTypeSelected).isTrue()
    }
}