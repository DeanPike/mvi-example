package au.com.deanpike.detail.data.converter

import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.data.converter.ConverterShared.advertiserConverter
import au.com.deanpike.detail.data.converter.ConverterShared.schoolConverter
import au.com.deanpike.network.model.external.detail.ChildListing
import au.com.deanpike.network.model.external.detail.ProjectDetail

object ProjectConverter {
    fun convertDetail(detail: ProjectDetail): au.com.deanpike.detail.client.model.detail.ProjectDetail {
        return au.com.deanpike.detail.client.model.detail.ProjectDetail(
            id = detail.id,
            listingType = ListingType.PROJECT,
            address = detail.address,
            headline = detail.headline,
            description = detail.description,
            media = detail.media?.let { ConverterShared.mediaConverter(it) } ?: emptyList(),
            advertiser = detail.advertiser?.let { advertiserConverter(it) },
            schools = schoolConverter(detail.schools),
            dwellingType = detail.dwellingType,
            childListings = detail.childListings.map {
                propertyChildConverter(it)
            },
            projectName = detail.projectName,
            projectColorHex = detail.projectColorHex,
            projectLogoImageUrl = detail.projectLogoImageUrl,
            showroomAddress = detail.showroomAddress
        )
    }

    private fun propertyChildConverter(child: ChildListing): ProjectChild {
        return ProjectChild(
            id = child.id,
            bedroomCount = child.bedroomCount?.toInt(),
            bathroomCount = child.bathroomCount?.toInt(),
            carSpaceCount = child.carspaceCount?.toInt(),
            price = child.price,
            propertyTypeDescription = child.propertyTypeDescription,
            propertyUrl = child.propertyUrl,
            propertyImage = child.propertyImage,
            lifecycleStatus = child.lifecycleStatus
        )
    }
}