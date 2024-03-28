package au.com.deanpike.client.data.datasource

import au.com.deanpike.client.model.PersonDTO
import java.util.UUID
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PersonDataSourceImplTest {

    private lateinit var dataSource: PersonDataSource

    @BeforeEach
    fun setupTest() {
        dataSource = PersonDataSourceImpl()
    }

    @Test
    fun `should initialise person map`() = runTest {
        assertThat(dataSource.getPeople()).isEmpty()
    }

    @Test
    fun `should add person`() = runTest {
        val person = PersonDTO(
            id = UUID.randomUUID(),
            name = "Name",
            surname = "Surname",
            age = 23
        )

        val addedId = dataSource.addPerson(person)

        assertThat(dataSource.getPeople().size).isEqualTo(1)
        assertThat(person.id).isEqualTo(addedId)
    }

    @Test
    fun `should add a person without an id`() = runTest {
        val person = PersonDTO(
            id = null,
            name = "Name",
            surname = "Surname",
            age = 23
        )

        assertThat(dataSource.getPeople()).isEmpty()
        val addedId = dataSource.addPerson(person)

        assertThat(dataSource.getPeople().size).isEqualTo(1)
        assertThat(addedId).isNotNull()
    }

    @Test
    fun `should update a person`() = runTest {
        val person = PersonDTO(
            id = UUID.randomUUID(),
            name = "Name",
            surname = "Surname",
            age = 23
        )
        dataSource.addPerson(person)

        assertThat(dataSource.getPeople().size).isEqualTo(1)

        val currentPerson = dataSource.getPerson(person.id!!)!!
        with(currentPerson) {
            assertThat(name).isEqualTo("Name")
            assertThat(surname).isEqualTo("Surname")
            assertThat(age).isEqualTo(23)
        }

        // Test
        val updatedPerson = person.copy(
            name = "New Name",
            surname = "New Surname",
            age = 43
        )
        dataSource.updatePerson(updatedPerson)

        assertThat(dataSource.getPeople().size).isEqualTo(1)

        val actualPerson = dataSource.getPerson(person.id!!)!!
        with(actualPerson) {
            assertThat(name).isEqualTo("New Name")
            assertThat(surname).isEqualTo("New Surname")
            assertThat(age).isEqualTo(43)
        }
    }

    @Test
    fun `should add a person that is trying to be updated but does not have an id`() = runTest {
        val person = PersonDTO(
            id = null,
            name = "Name",
            surname = "Surname",
            age = 23
        )

        assertThat(dataSource.getPeople()).isEmpty()

        dataSource.updatePerson(person)

        assertThat(dataSource.getPeople().size).isEqualTo(1)
    }

    @Test
    fun `should delete person`() = runTest {
        val person = PersonDTO(
            id = UUID.randomUUID(),
            name = "Name",
            surname = "Surname",
            age = 23
        )
        dataSource.addPerson(person)

        assertThat(dataSource.getPeople().size).isEqualTo(1)

        // Test
        dataSource.deletePerson(person)

        assertThat(dataSource.getPeople()).isEmpty()
    }
}