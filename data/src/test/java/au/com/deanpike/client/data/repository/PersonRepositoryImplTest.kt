package au.com.deanpike.client.data.repository

import au.com.deanpike.client.data.datasource.PersonDataSource
import au.com.deanpike.client.model.PersonDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PersonRepositoryImplTest {

    private lateinit var repo: PersonRepository
    private val dataSource: PersonDataSource = mockk()
    private val person = getPersonOne()

    @BeforeEach
    fun setupTest() {
        repo = PersonRepositoryImpl(dataSource)
    }

    @Test
    fun `should add person`() = runTest {
        coEvery { dataSource.addPerson(person) } returns UUID.randomUUID()

        val addedId = repo.addPerson(person)

        assertThat(addedId).isNotNull()
        coVerify { dataSource.addPerson(person) }
    }

    @Test
    fun `should get people`() = runTest {
        coEvery { dataSource.getPeople() } returns listOf(person, getPersonTwo())

        val response = repo.getPeople()

        assertThat(response.size).isEqualTo(2)

        assertThat("Name One").isEqualTo(response[0].name)
        assertThat("Surname One").isEqualTo(response[0].surname)
        assertThat(23).isEqualTo(response[0].age)

        assertThat("Name Two").isEqualTo(response[1].name)
        assertThat("Surname Two").isEqualTo(response[1].surname)
        assertThat(27).isEqualTo(response[1].age)

        coVerify { dataSource.getPeople() }
    }

    @Test
    fun `should get person`() = runTest {
        coEvery { dataSource.getPerson(person.id!!) } returns person

        val response = repo.getPerson(person.id!!)

        with(response!!) {
            assertThat("Name One").isEqualTo(name)
            assertThat("Surname One").isEqualTo(surname)
            assertThat(23).isEqualTo(age)
        }

        coVerify { dataSource.getPerson(person.id!!) }
    }

    @Test
    fun `should update person`() = runTest {
        coEvery { dataSource.updatePerson(person) } returns true

        val response = repo.updatePerson(person)

        assertThat(response).isTrue()
        coVerify { dataSource.updatePerson(person) }
    }

    @Test
    fun `should delete person`() = runTest {
        coEvery { dataSource.deletePerson(person) } returns true

        val response = repo.deletePerson(person)

        assertThat(response).isTrue()
        coVerify { dataSource.deletePerson(person) }
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