package au.com.deanpike.ui.screen.list

import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.client.usecase.PersonUseCase
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.testshared.extension.TestDispatcherExtension
import au.com.deanpike.uishared.base.ScreenStateType
import io.mockk.coEvery
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherExtension::class)
class PersonListViewModelTest {

    private val personUseCase: PersonUseCase = mockk()

    private lateinit var viewModel: PersonListViewModel

    @BeforeEach
    fun setupTest(dispatcherProvider: DispatcherProvider) {
        viewModel = PersonListViewModel(
            dispatcher = dispatcherProvider,
            personUseCase = personUseCase
        )
    }

    @Test
    fun `should initialise view model`() = runTest {
        coEvery { personUseCase.getPeople() } returns listOf(getPersonOne(), getPersonTwo())

        viewModel.setEvent(PersonListScreenEvent.Initialise)

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.SUCCESS)
            assertThat(2).isEqualTo(people.size)
            assertThat("Name One").isEqualTo(people[0].name)
            assertThat("Name Two").isEqualTo(people[1].name)
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