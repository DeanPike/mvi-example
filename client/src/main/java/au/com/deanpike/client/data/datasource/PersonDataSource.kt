package au.com.deanpike.client.data.datasource

import au.com.deanpike.client.model.PersonDTO
import java.util.UUID
import javax.inject.Inject

internal interface PersonDataSource {
    fun addPerson(person: PersonDTO)
    fun getPeople(): List<PersonDTO>
    fun getPerson(id: UUID): PersonDTO?
    fun updatePerson(person: PersonDTO)
    fun deletePerson(person: PersonDTO)
}

internal class PersonDataSourceImpl @Inject constructor() : PersonDataSource {
    private val personMap = mutableMapOf<UUID, PersonDTO>()

    init {
        personMap.clear()
    }

    override fun getPeople(): List<PersonDTO> {
        return personMap.values.toList()
    }

    override fun getPerson(id: UUID): PersonDTO? {
        return personMap[id]
    }

    override fun addPerson(person: PersonDTO) {
        person.id?.let { id ->
            personMap[id] = person
        }
    }

    override fun updatePerson(person: PersonDTO) {
        addPerson(person)
    }

    override fun deletePerson(person: PersonDTO) {
        personMap.remove(person.id)
    }
}