package au.com.deanpike.detail.client.model.detail

import au.com.deanpike.detail.client.model.type.PhoneNumberType

data class PhoneNumber(
    val type: PhoneNumberType,
    val label: String?,
    val number: String?
)
