package au.com.deanpike.data.converter

import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.network.model.external.listing.Advertiser
import au.com.deanpike.network.model.external.listing.EarliestInspection
import au.com.deanpike.network.model.external.listing.GeoLocation
import au.com.deanpike.network.model.external.listing.Images
import au.com.deanpike.network.model.external.listing.Medium
import au.com.deanpike.network.model.external.listing.Project
import au.com.deanpike.network.model.external.listing.ProjectChildListing
import au.com.deanpike.network.model.external.listing.SearchResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectConverterTest {
    @Test
    fun `create project`() {
        val childOne = ProjectChildListing(
            id = 11,
            dateListed = "2024-03-01T16:33:13+11:00",
            lifecycleStatus = "New",
            price = "900000",
            bathroomCount = 2,
            bedroomCount = 3,
            carspaceCount = 1,
            homepassEnabled = true,
            propertySize = "Small",
            earliestInspections = listOf(
                EarliestInspection(
                    timeOpen = "2024-04-03T10:00:00",
                    timeClose = "2024-04-03T12:00:00",
                    recurrenceType = "daily"
                ),
                EarliestInspection(
                    timeOpen = "2024-04-03T17:00:00",
                    timeClose = "2024-04-03T18:00:00",
                    recurrenceType = "weekly"
                )
            )
        )

        val externalProject = Project(
            projectName = "Project Name",
            projectBannerImage = "http://banner.image",
            projectLogoImage = "http://logo.image",
            projectPriceFrom = 1230000,
            projectColorHex = "White",
            childListings = listOf(childOne)
        )

        val result = SearchResult(
            id = 1234,
            listingType = ListingType.PROJECT,
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
            bedroomCount = 0,
            bathroomCount = 0,
            carspaceCount = 0,
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
            project = externalProject,
            promoLevel = "",
            topspot = null
        )

        val listing = ProjectConverter().convertListing(result)
        assertThat(listing).isInstanceOf(au.com.deanpike.client.model.listing.response.Project::class.java)
        val project = listing as au.com.deanpike.client.model.listing.response.Project

        with(project) {
            assertThat(id).isEqualTo(1234)
            assertThat(listingType).isEqualTo(ListingType.PROJECT)
            assertThat(address).isEqualTo("Property address")
            assertThat(bannerImage).isEqualTo("http://banner.image")
            assertThat(logoImage).isEqualTo("http://logo.image")
            assertThat(projectName).isEqualTo("Project Name")
            assertThat(properties.size).isEqualTo(1)
            assertThat(projectColour).isEqualTo("White")
            assertThat(listingImage).isEqualTo("http://some.image.one")
        }

        with(project.properties[0]) {
            assertThat(id).isEqualTo(11)
            assertThat(listingType).isEqualTo(ListingType.PROPERTY)
            assertThat(lifecycleStatus).isEqualTo("New")
            assertThat(listingDetails.price).isEqualTo("900000")
            assertThat(listingDetails.numberOfBedrooms).isEqualTo(3)
            assertThat(listingDetails.numberOfBathrooms).isEqualTo(2)
            assertThat(listingDetails.numberOfCarSpaces).isEqualTo(1)
        }
    }
}