package au.com.deanpike.listings.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.component.FilterComponent
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_LAYOUT
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.LISTING_TYPE
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.STATUS_BUTTON
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.waitUntilTagExists
import org.assertj.core.api.Assertions.assertThat

class FilterComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<FilterComponentRobot, FilterComponentRobotInitData>(composeRule) {
    private var statusSelected = StatusType.RENT
    private var listingTypeSelected = false

    override fun setupComponent(data: FilterComponentRobotInitData?): FilterComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                FilterComponent(
                    selectedStatus = data!!.status,
                    selectedListingTypes = data.listingTypes,
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

    override fun assertLayoutDisplayed(): FilterComponentRobot {
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

    fun clickStatusButton(): FilterComponentRobot {
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

    fun waitUntilLayoutShown(): FilterComponentRobot {
        composeRule.advanceTimeAndWait()
        composeRule.waitUntilTagExists(tag = FILTER_COMPONENT_LAYOUT, timeout = 2000)
        return this
    }
}

data class FilterComponentRobotInitData(
    val status: StatusType = StatusType.RENT,
    val listingTypes: List<DwellingType> = listOf(DwellingType.HOUSE)
) : TestRobotInitData