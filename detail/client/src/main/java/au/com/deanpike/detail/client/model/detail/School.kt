package au.com.deanpike.detail.client.model.detail

data class School(
    val id: Int,
    val address: String? = null,
    val yearRange: String? = null,
    val name: String? = null,
    val educationLevel: String? = null,
    val gender: String? = null,
    val system: String? = null
)