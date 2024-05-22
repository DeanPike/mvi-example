package au.com.deanpike.detail.data.converter

import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.Media
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.detail.School
import au.com.deanpike.detail.client.model.type.MediaType
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.network.model.external.detail.AgencyListingContacts
import au.com.deanpike.network.model.external.detail.PropertyDetail
import au.com.deanpike.network.model.external.detail.PhoneNumbers
import au.com.deanpike.network.model.external.detail.Schools

class PropertyConverter {
    fun convertDetail(detail: PropertyDetail): au.com.deanpike.detail.client.model.detail.PropertyDetail {
        return au.com.deanpike.detail.client.model.detail.PropertyDetail(
            id = detail.id,
            listingType = ListingType.PROPERTY,
            address = detail.address,
            headline = detail.headline,
            description = detail.description,
            lifecycleStatus = detail.lifecycleStatus,
            media = mediaConverter(detail.media),
            advertiser = detail.advertiser?.let { advertiserConverter(it) },
            schools = schoolConverter(detail.schools),
            dwellingType = detail.dwellingType,
            price = detail.price,
            bedroomCount = detail.bedroomCount,
            bathroomCount = detail.bathroomCount,
            carSpaceCount = detail.carspaceCount,
            dateSold = detail.saleMetaData?.dateSold,
            saleType = detail.saleMetaData?.saleType
        )
    }

    private fun mediaConverter(items: List<au.com.deanpike.network.model.external.detail.Media>): List<Media> {
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

    private fun advertiserConverter(item: au.com.deanpike.network.model.external.detail.Advertiser): Advertiser {
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
                responses.add(
                    PhoneNumber(
                        type = type,
                        label = item.displayLabel,
                        number = item.number
                    )
                )
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

    private fun schoolConverter(items: List<Schools>): List<School> {
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
}