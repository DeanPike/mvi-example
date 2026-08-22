package au.com.deanpike.listings.ui.list

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.GeoLocation
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.Property
import org.junit.Assert.assertEquals
import org.junit.Test

class ListingMapPinsTest {

    @Test
    fun `maps properties and projects with a geo location to pins`() {
        val property = getProperty(id = 1, geoLocation = GeoLocation(latitude = 1.1, longitude = 2.2))
        val project = getProject(id = 2, geoLocation = GeoLocation(latitude = 3.3, longitude = 4.4))

        val pins = toMapPins(listOf(property, project))

        assertEquals(2, pins.size)

        assertEquals(1L, pins[0].listingId)
        assertEquals(ListingType.PROPERTY, pins[0].listingType)
        assertEquals(1.1, pins[0].position.latitude, 0.0)
        assertEquals(2.2, pins[0].position.longitude, 0.0)

        assertEquals(2L, pins[1].listingId)
        assertEquals(ListingType.PROJECT, pins[1].listingType)
        assertEquals(3.3, pins[1].position.latitude, 0.0)
        assertEquals(4.4, pins[1].position.longitude, 0.0)
    }

    @Test
    fun `excludes listings without a geo location`() {
        val propertyWithoutGeoLocation = getProperty(id = 1, geoLocation = null)
        val projectWithoutGeoLocation = getProject(id = 2, geoLocation = null)
        val propertyWithGeoLocation = getProperty(id = 3, geoLocation = GeoLocation(latitude = 1.1, longitude = 2.2))

        val pins = toMapPins(listOf(propertyWithoutGeoLocation, projectWithoutGeoLocation, propertyWithGeoLocation))

        assertEquals(1, pins.size)
        assertEquals(3L, pins[0].listingId)
    }

    @Test
    fun `returns an empty list when there are no listings`() {
        val pins = toMapPins(emptyList())

        assertEquals(emptyList<MapPin>(), pins)
    }

    private fun getProperty(id: Long, geoLocation: GeoLocation?): Property {
        return Property(
            id = id,
            listingType = ListingType.PROPERTY,
            address = "Property address",
            listingImage = "http://listing.image",
            agencyImage = "http://agency.image",
            dwellingType = "House",
            headLine = "Property headline",
            lifecycleStatus = "New",
            agencyColour = "White",
            geoLocation = geoLocation,
            detail = ListingDetails(
                price = "$1000000",
                numberOfBedrooms = 4,
                numberOfBathrooms = 3,
                numberOfCarSpaces = 2
            )
        )
    }

    private fun getProject(id: Long, geoLocation: GeoLocation?): Project {
        return Project(
            id = id,
            listingType = ListingType.PROJECT,
            address = "Project address",
            listingImage = "http://listing.image",
            bannerImage = "http://banner.image",
            logoImage = "http://logo.image",
            projectName = "Project name",
            projectColour = "White",
            properties = emptyList(),
            geoLocation = geoLocation
        )
    }
}
