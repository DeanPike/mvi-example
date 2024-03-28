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
    fun `should not add a person with an id`() = runTest {
        assertThat(dataSource.getPeople()).isEmpty()

        val addedId = dataSource.addPerson(getPerson())

        assertThat(dataSource.getPeople()).isEmpty()
        assertThat(addedId).isNull()
    }

    @Test
    fun `should add a person`() = runTest {
        assertThat(dataSource.getPeople()).isEmpty()
        val addedId = dataSource.addPerson(getPerson().copy(id = null))

        assertThat(dataSource.getPeople().size).isEqualTo(1)
        assertThat(addedId).isNotNull()
    }

    @Test
    fun `should update a person`() = runTest {
        val person = getPerson()
        val newId = dataSource.addPerson(person.copy(id = null))

        assertThat(newId).isNotIn()
        val people = dataSource.getPeople()
        assertThat(people.size).isEqualTo(1)
        assertThat(people[0].name).isEqualTo(person.name)

        val currentPerson = dataSource.getPerson(newId!!)!!
        with(currentPerson) {
            assertThat(id).isEqualTo(newId)
            assertThat(name).isEqualTo("Name")
            assertThat(surname).isEqualTo("Surname")
            assertThat(age).isEqualTo(23)
        }

        // Test
        val updatedPerson = person.copy(
            id = newId,
            name = "New Name",
            surname = "New Surname",
            age = 43
        )
        val response = dataSource.updatePerson(updatedPerson)

        assertThat(dataSource.getPeople().size).isEqualTo(1)
        assertThat(response).isTrue()

        val actualPerson = dataSource.getPerson(updatedPerson.id!!)!!
        with(actualPerson) {
            assertThat(id).isEqualTo(newId)
            assertThat(name).isEqualTo("New Name")
            assertThat(surname).isEqualTo("New Surname")
            assertThat(age).isEqualTo(43)
        }
    }

    @Test
    fun `should not update a person without an id`() = runTest {
        assertThat(dataSource.getPeople()).isEmpty()

        val response = dataSource.updatePerson(getPerson().copy(id = null))

        assertThat(response).isFalse()
        assertThat(dataSource.getPeople()).isEmpty()
    }

    @Test
    fun `should not update a person that could not be found`() = runTest {
        val person = getPerson()
        val newId = dataSource.addPerson(person.copy(id = null))

        assertThat(newId).isNotNull()
        assertThat(dataSource.getPeople().size).isEqualTo(1)

        val updatedPerson = person.copy(
            id = UUID.randomUUID(),
            name = "New name",
            surname = "New surname",
            age = 99
        )

        assertThat(newId).isNotEqualTo(updatedPerson.id)

        val updateResponse = dataSource.updatePerson(updatedPerson)

        assertThat(updateResponse).isFalse()
        val people = dataSource.getPeople()
        assertThat(people.size).isEqualTo(1)
        assertThat(people[0].id).isEqualTo(newId)
        val currentPerson = people[0]

        with(currentPerson) {
            assertThat(id!!).isEqualTo(newId)
            assertThat(name).isEqualTo("Name")
            assertThat(surname).isEqualTo("Surname")
            assertThat(age).isEqualTo(23)
        }
    }

    @Test
    fun `should delete person`() = runTest {
        val person = getPerson().copy(id = null)
        val newId = dataSource.addPerson(person)

        assertThat(newId).isNotNull()
        assertThat(dataSource.getPeople().size).isEqualTo(1)

        // Test
        val response = dataSource.deletePerson(person.copy(id = newId))

        assertThat(dataSource.getPeople()).isEmpty()
        assertThat(response).isTrue()
    }

    @Test
    fun `should not delete a person that cannot be found`() = runTest {
        val person = getPerson().copy(id = null)
        val newId = dataSource.addPerson(person)
        assertThat(dataSource.getPeople().size).isEqualTo(1)

        val personToDelete = person.copy(id = UUID.randomUUID())

        assertThat(newId).isNotEqualTo(personToDelete.id)

        val response = dataSource.deletePerson(personToDelete)

        assertThat(response).isFalse()
        assertThat(dataSource.getPeople().size).isEqualTo(1)
    }

    private fun getPerson() = PersonDTO(
        id = UUID.randomUUID(),
        name = "Name",
        surname = "Surname",
        age = 23
    )
}