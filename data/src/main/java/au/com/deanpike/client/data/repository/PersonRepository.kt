package au.com.deanpike.client.data.repository

import au.com.deanpike.client.data.datasource.PersonDataSource
import au.com.deanpike.client.model.PersonDTO
import java.util.UUID
import javax.inject.Inject

internal interface PersonRepository {
    suspend fun addPerson(person: PersonDTO)
    suspend fun getPeople(): List<PersonDTO>
    suspend fun getPerson(id: UUID): PersonDTO?
    suspend fun updatePerson(person: PersonDTO)
    suspend fun deletePerson(person: PersonDTO)
}

internal class PersonRepositoryImpl @Inject constructor(
    private val dataSource: PersonDataSource
) : PersonRepository {
    override suspend fun addPerson(person: PersonDTO) {
        dataSource.addPerson(person)
    }

    override suspend fun getPeople(): List<PersonDTO> {
        return dataSource.getPeople()
    }

    override suspend fun getPerson(id: UUID): PersonDTO? {
        return dataSource.getPerson(id)
    }

    override suspend fun updatePerson(person: PersonDTO) {
        dataSource.updatePerson(person)
    }

    override suspend fun deletePerson(person: PersonDTO) {
        dataSource.deletePerson(person)
    }
}