package au.com.deanpike.commonshared.model

import au.com.deanpike.commonshared.type.MediaType

data class Media(
    val mediaType: MediaType,
    val url: String
)
