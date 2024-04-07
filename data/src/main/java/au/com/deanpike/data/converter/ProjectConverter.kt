package au.com.deanpike.data.converter

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Project
import au.com.deanpike.client.model.listing.response.ProjectChild
import au.com.deanpike.network.model.external.SearchResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProjectConverter @Inject constructor() : ListingConverter {

    override fun convertListing(listing: SearchResult): Listing {
        val properties = listing.project?.childListings?.map {
            ProjectChild(
                id = it.id,
                listingType = ListingType.PROPERTY,
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
            listingType = listing.listingType,
            address = listing.address,
            bannerImage = listing.project?.projectBannerImage,
            logoImage = listing.project?.projectLogoImage,
            projectName = listing.project?.projectName,
            projectColour = listing.project?.projectColorHex,
            properties = properties
        )
    }
}