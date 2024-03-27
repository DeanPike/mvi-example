package au.com.deanpike.ui.unit.screen.list

import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.ui.ability.PersonListItemAbility
import au.com.deanpike.ui.ability.PersonListScreenAbility
import au.com.deanpike.ui.screen.list.PersonListScreenContent
import au.com.deanpike.ui.screen.list.PersonListScreenState
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiTestBase
import java.util.UUID
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PersonListScreenTest : UiTestBase() {

    private val listAbility = PersonListScreenAbility(composeTestRule)
    private val itemAbility = PersonListItemAbility(composeTestRule)

    @Test
    fun should_show_screen_with_peopleLoaded() {
        var itemClicked: UUID? = null
        var wasFabClicked = false
        val personOne = getPersonOne()
        val personTwo = getPersonTwo()
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PersonListScreenContent(
                        state = PersonListScreenState(
                            screenState = ScreenStateType.SUCCESS,
                            people = listOf(personOne, personTwo)
                        ),
                        onItemClicked = {
                            itemClicked = it
                        },
                        onFabClicked = {
                            wasFabClicked = true
                        }

                    )
                }
            }
            advanceTimeAndWait(this)

            with(listAbility) {
                assertListDisplayed()
                assertFalse(wasFabClicked)
                clickOnFab()
                assertTrue(wasFabClicked)
            }

            with(itemAbility) {
                assertNameLabelDisplayed(0)
                assertNameDisplayed(position = 0, text = "Name One Surname One")
                assertAgeLabelDisplayed(0)
                assertAgeDisplayed(position = 0, text = "11")

                assertNameLabelDisplayed(1)
                assertNameDisplayed(position = 1, text = "Name Two Surname Two")
                assertAgeLabelDisplayed(1)
                assertAgeDisplayed(position = 1, text = "22")

                clickOnItem(0)
                assertNotNull(itemClicked)
                assertEquals(personOne.id, itemClicked)

                clickOnItem(1)
                assertEquals(personTwo.id, itemClicked)
            }
        }
    }

    private fun getPersonOne() = PersonDTO(
        id = UUID.randomUUID(),
        name = "Name One",
        surname = "Surname One",
        age = 11
    )

    private fun getPersonTwo() = PersonDTO(
        id = UUID.randomUUID(),
        name = "Name Two",
        surname = "Surname Two",
        age = 22
    )

}