package au.com.deanpike.client.model

import java.util.UUID

data class PersonDTO(
    val id: UUID?,
    val name: String,
    val surname: String,
    val age: Int
)