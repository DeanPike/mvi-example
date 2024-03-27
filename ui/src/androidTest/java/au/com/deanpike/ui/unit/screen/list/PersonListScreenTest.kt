package au.com.deanpike.ui.unit.screen.list

import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.ui.screen.list.PersonListScreenContent
import au.com.deanpike.ui.screen.list.PersonListScreenState
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiTestBase
import java.util.UUID
import org.junit.Test

class PersonListScreenTest : UiTestBase() {

    @Test
    fun should_show_screen_with_peopleLoaded() {
        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    PersonListScreenContent(
                        state = PersonListScreenState(
                            screenState = ScreenStateType.SUCCESS,
                            people = listOf(getPersonOne(), getPersonTwo())
                        )
                    )
                }
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
        age = 11
    )

}