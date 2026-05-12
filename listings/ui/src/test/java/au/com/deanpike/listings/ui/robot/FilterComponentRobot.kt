package au.com.deanpike.listings.ui.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.ui.list.component.FilterComponent
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_DWELLING_TYPE_LABEL
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_DWELLING_TYPE_TEXT
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_FILTER_BUTTON
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_FILTER_IMAGE
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_LAYOUT
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_STATUS_LABEL
import au.com.deanpike.listings.ui.list.component.FilterComponentTestTags.FILTER_COMPONENT_STATUS_TEXT
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.disableAnimations

class FilterComponentRobot(composeRule: ComposeContentTestRule) : TestRobotBase<FilterComponentRobot, FilterComponentRobotInitData>(composeRule) {
    var eventCalled: Boolean = false

    override fun setupComponent(data: FilterComponentRobotInitData?) = apply {
        composeRule.setContent {
            MviExampleTheme {
                FilterComponent(
                    selectedStatus = data!!.status,
                    selectedDwellingTypes = data.dwellingTypes,
                    onEvent = {
                        eventCalled = true
                    },
                )

            }
        }
        composeRule.disableAnimations()
    }

    override fun assertLayoutDisplayed() = apply {
        composeRule.advanceTimeAndWait()
        composeRule.assertTagDisplayed(FILTER_COMPONENT_LAYOUT)
    }

    fun assertStatusText(text: String) = apply {
        composeRule.assertTextDisplayed(tag = FILTER_COMPONENT_STATUS_LABEL, text = "Status")
        composeRule.assertTextDisplayed(tag = FILTER_COMPONENT_STATUS_TEXT, text = text)
    }

    fun assertDwellingText(text: String) = apply {
        composeRule.assertTextDisplayed(tag = FILTER_COMPONENT_DWELLING_TYPE_LABEL, text = "Property type")
        composeRule.assertTextDisplayed(tag = FILTER_COMPONENT_DWELLING_TYPE_TEXT, text = text)
    }

    fun assertFilterButtonDisplayed() = apply {
        composeRule.assertTagDisplayed(FILTER_COMPONENT_FILTER_BUTTON)
        composeRule.assertDrawableDisplayed(tag = FILTER_COMPONENT_FILTER_IMAGE, drawable = R.drawable.outline_format_list_bulleted_24)
    }

    fun clickFilterButton() = apply {
        composeRule.clickOn(FILTER_COMPONENT_FILTER_BUTTON)
        composeRule.advanceTimeAndWait()
    }
}

data class FilterComponentRobotInitData(
    val status: StatusType,
    val dwellingTypes: List<DwellingType>
) : TestRobotInitData