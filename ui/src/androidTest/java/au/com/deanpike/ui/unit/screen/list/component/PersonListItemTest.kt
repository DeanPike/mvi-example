//package au.com.deanpike.ui.unit.screen.list.component
//
//import au.com.deanpike.client.model.PersonDTO
//import au.com.deanpike.ui.ability.PersonListItemAbility
//import au.com.deanpike.ui.screen.list.component.PersonListItem
//import au.com.deanpike.uishared.theme.MviExampleTheme
//import au.com.deanpike.uitestshared.base.UiTestBase
//import java.util.UUID
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Test
//
//class PersonListItemTest : UiTestBase() {
//
//    private val ability = PersonListItemAbility(composeTestRule)
//
//    @Test
//    fun show_person_list_item() {
//        var wasItemClicked: UUID? = null
//        val person = getPerson()
//        with(composeTestRule) {
//            setContent {
//                MviExampleTheme {
//                    PersonListItem(
//                        position = 0,
//                        person = person,
//                        onItemClicked = {
//                            wasItemClicked = it
//                        }
//                    )
//                }
//            }
//            advanceTimeAndWait(this)
//
//            with(ability) {
//                assertNameLabelDisplayed(0)
//                assertNameDisplayed(position = 0, text = "Name One Surname One")
//                assertAgeLabelDisplayed(0)
//                assertAgeDisplayed(position = 0, text = "11")
//                clickOnItem(0)
//            }
//
//            assertNotNull(wasItemClicked)
//            assertEquals(person.id, wasItemClicked)
//        }
//    }
//
//    private fun getPerson() = PersonDTO(
//        id = UUID.randomUUID(),
//        name = "Name One",
//        surname = "Surname One",
//        age = 11
//    )
//
//}