package au.com.deanpike.detail.client.model.detail

data class Agent(
    val id: String?,
    val address: String?,
    val name: String?,
    val imageUrl: String?,
    val emailAddress: String?,
    val phoneNumbers: List<PhoneNumber>
)
