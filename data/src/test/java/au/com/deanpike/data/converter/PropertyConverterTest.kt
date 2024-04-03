package au.com.deanpike.data.converter

import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.data.model.external.Advertiser
import au.com.deanpike.data.model.external.GeoLocation
import au.com.deanpike.data.model.external.Images
import au.com.deanpike.data.model.external.Medium
import au.com.deanpike.data.model.external.SearchResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PropertyConverterTest {
    @Test
    fun `create property`() {
        val result = SearchResult(
            id = 1234,
            listingType = ListingType.PROPERTY,
            media = listOf(
                Medium(
                    type = "image",
                    imageUrl = "http://some.image.one",
                    mediaType = "image"
                ),
                Medium(
                    type = "image",
                    imageUrl = "http://some.image.two",
                    mediaType = "image"
                )
            ),
            address = "Property address",
            advertiser = Advertiser(
                id = 1,
                images = Images("http://advertiser.logo"),
                agencyListingContacts = emptyList(),
                name = "Advertiser Name",
                preferredColorHex = "White"
            ),
            price = "1500000",
            bedroomCount = 4,
            bathroomCount = 3,
            carspaceCount = 2,
            dwellingType = "House",
            headline = "Property Headline",
            lifecycleStatus = "New",
            additionalFeatures = emptyList(),
            auctionDate = null,
            dateListed = null,
            earliestInspections = emptyList(),
            geoLocation = GeoLocation(latitude = 1.1, longitude = 2.2),
            hasVideo = false,
            homepassEnabled = false,
            landArea = null,
            largeLand = false,
            metadata = null,
            project = null,
            promoLevel = "",
            topspot = null
        )

        val listing = PropertyConverter().convertListing(result)
        assertThat(listing).isInstanceOf(Property::class.java)
        val property = listing as Property

        with(property) {
            assertThat(id).isEqualTo(1234)
            assertThat(listingType).isEqualTo(ListingType.PROPERTY)
            assertThat(address).isEqualTo("Property address")
            assertThat(listingImage).isEqualTo("http://some.image.one")
            assertThat(agencyImage).isEqualTo("http://advertiser.logo")
            assertThat(agencyColour).isEqualTo("White")
            assertThat(dwellingType).isEqualTo("House")
            assertThat(headLine).isEqualTo("Property Headline")
            assertThat(lifecycleStatus).isEqualTo("New")
            assertThat(detail.price).isEqualTo("1500000")
            assertThat(detail.numberOfBedrooms).isEqualTo(4)
            assertThat(detail.numberOfBathrooms).isEqualTo(3)
            assertThat(detail.numberOfCarSpaces).isEqualTo(2)
        }
    }
}