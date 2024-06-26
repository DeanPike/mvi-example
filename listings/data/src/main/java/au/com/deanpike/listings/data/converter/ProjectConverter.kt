package au.com.deanpike.listings.data.converter

import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Listing
import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.datashared.util.ListingTypeConverter
import au.com.deanpike.network.model.external.listing.SearchResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProjectConverter @Inject constructor() : ListingConverter {

    override fun convertListing(listing: SearchResult): Listing {
        val properties = listing.project?.childListings?.map {
            ProjectChild(
                id = it.id,
                listingType = ListingType.PROPERTY,
                lifecycleStatus = it.lifecycleStatus,
                listingDetails = ListingDetails(
                    price = it.price,
                    numberOfBedrooms = it.bedroomCount?.toInt(),
                    numberOfBathrooms = it.bathroomCount?.toInt(),
                    numberOfCarSpaces = it.carspaceCount?.toInt()
                )
            )
        } ?: emptyList()

        return Project(
            id = listing.id,
            listingType = ListingTypeConverter.getListingType(listing.listingType),
            address = listing.address,
            listingImage = listing.media?.firstOrNull {
                it.mediaType == "image"
            }?.imageUrl,
            bannerImage = listing.project?.projectBannerImage,
            logoImage = listing.project?.projectLogoImage,
            projectName = listing.project?.projectName,
            projectColour = listing.project?.projectColorHex,
            properties = properties
        )
    }
}