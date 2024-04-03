package au.com.deanpike.data.converter

import au.com.deanpike.client.model.listing.response.Listing
import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.data.model.external.SearchResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PropertyConverter @Inject constructor() : ListingConverter {
    override fun convertListing(listing: SearchResult): Listing {
        return Property(
            id = listing.id,
            listingType = listing.listingType,
            listingImage = listing.media.firstOrNull()?.imageUrl,
            address = listing.address,
            agencyImage = listing.advertiser?.images?.logoUrl,
            agencyColour = listing.advertiser?.preferredColorHex,
            detail = ListingDetails(
                price = listing.price,
                numberOfBedrooms = listing.bedroomCount?.toInt(),
                numberOfBathrooms = listing.bathroomCount?.toInt(),
                numberOfCarSpaces = listing.carspaceCount?.toInt()
            ),
            dwellingType = listing.dwellingType,
            headLine = listing.headline,
            lifecycleStatus = listing.lifecycleStatus
        )
    }
}