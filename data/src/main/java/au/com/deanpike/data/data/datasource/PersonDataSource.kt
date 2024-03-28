package au.com.deanpike.data.data.datasource

import au.com.deanpike.client.model.PersonDTO
import java.util.UUID
import javax.inject.Inject

internal interface PersonDataSource {
    suspend fun addPerson(person: PersonDTO): UUID?
    suspend fun getPeople(): List<PersonDTO>
    suspend fun getPerson(id: UUID): PersonDTO?
    suspend fun updatePerson(person: PersonDTO): Boolean
    suspend fun deletePerson(person: PersonDTO): Boolean
}

// A data source to hold the people.
internal class PersonDataSourceImpl @Inject constructor() : PersonDataSource {
    private val personMap = mutableMapOf<UUID, PersonDTO>()

    init {
        personMap.clear()
    }

    override suspend fun getPeople(): List<PersonDTO> {
        return personMap.values.toList()
    }

    override suspend fun getPerson(id: UUID): PersonDTO? {
        return personMap[id]
    }

    override suspend fun addPerson(person: PersonDTO): UUID? {
        return if (person.id != null) {
            null
        } else {
            val personToAdd = person.copy(
                id = UUID.randomUUID()
            )
            personToAdd.id?.let {
                personMap[it] = personToAdd
            }

            personToAdd.id!!
        }
    }

    override suspend fun updatePerson(person: PersonDTO): Boolean {
        return if (person.id == null) {
            false
        } else {
            if (personMap.containsKey(person.id)) {
                personMap[person.id!!] = person
                true
            } else {
                false
            }
        }
    }

    override suspend fun deletePerson(person: PersonDTO): Boolean {
        return if (personMap.containsKey(person.id)) {
            personMap.remove(person.id)
            true
        } else {
            false
        }
    }
}