package au.com.deanpike.data.usecase

import au.com.deanpike.data.repository.PersonRepository
import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.client.usecase.PersonUseCase
import java.util.UUID
import javax.inject.Inject


// This class would usually maps the local data types to remote data types
internal class PersonUseCaseImpl @Inject constructor(
    private val repository: PersonRepository
) : PersonUseCase {
    override suspend fun addPerson(person: PersonDTO): UUID? {
        return repository.addPerson(person)
    }

    override suspend fun getPeople(): List<PersonDTO> {
        return repository.getPeople()
    }

    override suspend fun getPerson(id: UUID): PersonDTO? {
        return repository.getPerson(id)
    }

    override suspend fun updatePerson(person: PersonDTO): Boolean {
        return repository.updatePerson(person)
    }

    override suspend fun deletePerson(person: PersonDTO): Boolean {
        return repository.deletePerson(person)
    }
}