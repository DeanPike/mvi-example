package au.com.deanpike.detail.data.converter

import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.Media
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.School
import au.com.deanpike.detail.client.model.type.MediaType
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.network.model.external.detail.AgencyListingContacts
import au.com.deanpike.network.model.external.detail.PhoneNumbers

object ConverterShared {
    fun mediaConverter(items: List<au.com.deanpike.network.model.external.detail.Media>): List<Media> {
        val responses = mutableListOf<Media>()
        items.filter {
            it.mediaType == "image"
        }.forEach { item ->
            if (item.imageUrl != null && item.type != null) {
                responses.add(
                    Media(
                        mediaType = mediaTypeConverter(item.type!!),
                        url = item.imageUrl!!
                    )
                )
            }
        }

        return responses
    }

    fun advertiserConverter(item: au.com.deanpike.network.model.external.detail.Advertiser): Advertiser {
        return Advertiser(
            id = item.id,
            name = item.name,
            address = item.address,
            logoUrl = item.images?.logoUrl,
            agencyBannerImageUrl = item.images?.agencyBannerImageUrl,
            preferredColorHex = item.preferredColorHex,
            agencyListingContacts = agencyContactsConverter(item.agencyListingContacts)
        )
    }

    fun schoolConverter(items: List<au.com.deanpike.network.model.external.detail.School>): List<School> {
        val responses = mutableListOf<School>()
        items.forEach { item ->
            responses.add(
                School(
                    id = item.id,
                    address = item.address,
                    yearRange = item.yearRange,
                    name = item.name,
                    educationLevel = item.educationLevel,
                    gender = item.gender,
                    system = item.system
                )
            )
        }

        return responses
    }

    private fun agencyContactsConverter(items: List<AgencyListingContacts>): List<Agent> {
        val responses = mutableListOf<Agent>()
        items.forEach { item ->
            responses.add(
                Agent(
                    id = item.agentId,
                    address = item.address,
                    name = item.displayFullName,
                    imageUrl = item.imageUrl,
                    emailAddress = item.emailAddress,
                    phoneNumbers = phoneNumberConverter(item.phoneNumbers)
                )
            )
        }

        return responses
    }

    private fun phoneNumberConverter(items: List<PhoneNumbers>): List<PhoneNumber> {
        val responses = mutableListOf<PhoneNumber>()
        items.forEach { item ->
            phoneNumberTypeConverter(item.type)?.let { type ->
                if (item.displayLabel != null && item.number != null) {
                    responses.add(
                        PhoneNumber(
                            type = type,
                            label = item.displayLabel,
                            number = item.number
                        )
                    )
                }
            }
        }

        return responses
    }

    private fun phoneNumberTypeConverter(type: String?): PhoneNumberType? {
        return when (type) {
            "Mobile" -> {
                PhoneNumberType.MOBILE
            }
            "General" -> {
                PhoneNumberType.GENERAL
            }
            "Fax" -> {
                PhoneNumberType.FAX
            }
            else -> {
                null
            }
        }
    }

    private fun mediaTypeConverter(type: String): MediaType {
        return when (type) {
            "photo" -> {
                MediaType.PHOTO
            }
            "floor_plan" -> {
                MediaType.FLOOR_PLAN
            }
            else -> {
                MediaType.UNKNOWN
            }
        }
    }

}