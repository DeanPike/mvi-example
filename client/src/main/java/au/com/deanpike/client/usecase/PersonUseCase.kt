package au.com.deanpike.client.usecase

import au.com.deanpike.client.model.PersonDTO
import java.util.UUID

interface PersonUseCase {
    suspend fun addPerson(person: PersonDTO)
    suspend fun getPeople(): List<PersonDTO>
    suspend fun getPerson(id: UUID): PersonDTO?
    suspend fun updatePerson(person: PersonDTO)
    suspend fun deletePerson(person: PersonDTO)
}