package au.com.deanpike.detail.client.model.detail

data class ProjectChild(
    val id: Long,
    val bedroomCount: Int? = null,
    val bathroomCount: Int? = null,
    val carSpaceCount: Int? = null,
    val price: String? = null,
    val propertyTypeDescription: String? = null,
    val propertyUrl: String? = null,
    val propertyImage: String? = null,
    val lifecycleStatus: String? = null
)