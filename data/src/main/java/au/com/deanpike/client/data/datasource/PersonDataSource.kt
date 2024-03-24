package au.com.deanpike.client.data.datasource

import au.com.deanpike.client.model.PersonDTO
import java.util.UUID
import javax.inject.Inject

internal interface PersonDataSource {
    suspend fun addPerson(person: PersonDTO)
    suspend fun getPeople(): List<PersonDTO>
    suspend fun getPerson(id: UUID): PersonDTO?
    suspend fun updatePerson(person: PersonDTO)
    suspend fun deletePerson(person: PersonDTO)
}

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

    override suspend fun addPerson(person: PersonDTO) {
        person.id?.let { id ->
            personMap[id] = person
        }
    }

    override suspend fun updatePerson(person: PersonDTO) {
        addPerson(person)
    }

    override suspend fun deletePerson(person: PersonDTO) {
        personMap.remove(person.id)
    }
}