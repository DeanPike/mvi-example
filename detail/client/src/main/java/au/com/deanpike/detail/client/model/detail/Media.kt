package au.com.deanpike.detail.client.model.detail

import au.com.deanpike.detail.client.model.type.MediaType

data class Media(
    val mediaType: MediaType,
    val url: String
)
