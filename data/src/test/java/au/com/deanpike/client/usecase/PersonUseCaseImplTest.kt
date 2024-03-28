package au.com.deanpike.client.usecase

import au.com.deanpike.data.repository.PersonRepository
import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.data.usecase.PersonUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PersonUseCaseImplTest {
    private lateinit var useCase: PersonUseCase
    private val repo: PersonRepository = mockk()
    private val person = getPersonOne()

    @BeforeEach
    fun setupTest() {
        useCase = PersonUseCaseImpl(repo)
    }

    @Test
    fun `should add person`() = runTest {
        coEvery { repo.addPerson(person) } returns UUID.randomUUID()

        val id = useCase.addPerson(person)

        assertThat(id).isNotNull
        coVerify { repo.addPerson(person) }
    }

    @Test
    fun `should get people`() = runTest {
        val personTwo = getPersonTwo()
        coEvery { repo.getPeople() } returns listOf(person, personTwo)

        val people = useCase.getPeople()
        assertThat(people.size).isEqualTo(2)

        coVerify { repo.getPeople() }
    }

    @Test
    fun `should get person`() = runTest {
        coEvery { repo.getPerson(person.id!!) } returns person

        useCase.getPerson(person.id!!)

        coVerify { repo.getPerson(person.id!!) }
    }

    @Test
    fun `should update person`() = runTest {
        coEvery { repo.updatePerson(person) } returns true

        useCase.updatePerson(person)

        coVerify { repo.updatePerson(person) }
    }

    @Test
    fun `should delete person`() = runTest {
        coEvery { repo.deletePerson(person) } returns true

        useCase.deletePerson(person)

        coVerify { repo.deletePerson(person) }
    }

    private fun getPersonOne() = PersonDTO(
        id = UUID.randomUUID(),
        name = "Name One",
        surname = "Surname One",
        age = 23
    )

    private fun getPersonTwo() = PersonDTO(
        id = UUID.randomUUID(),
        name = "Name Two",
        surname = "Surname Two",
        age = 27
    )
}