package au.com.deanpike.ui.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.screen.list.PersonListScreenTestTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.clickOn

class PersonListScreenAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertListDisplayed() {
        composeTestRule.assertTagDisplayed(PersonListScreenTestTags.PERSON_LIST)
    }

    fun clickOnFab() {
        composeTestRule.clickOn(PersonListScreenTestTags.PERSON_LIST_FAB)
    }
}