package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class PropertyDetailScreenAbility(private val composeTestRule: ComposeContentTestRule) {

    fun assertScreenDisplayed() {
        composeTestRule.waitUntilTagExists(PROPERTY_DETAILS_LAYOUT, 1000)
        composeTestRule.assertTagDisplayed(PROPERTY_DETAILS_LAYOUT)
    }

    fun assertProgressDisplayed() {
        composeTestRule.assertTagDisplayed(PROPERTY_DETAIL_PROGRESS)
    }

    fun assertProgressNotDisplayed() {
        composeTestRule.assertTagDoesNotExist(PROPERTY_DETAIL_PROGRESS)
    }
}