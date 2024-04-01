package au.com.deanpike.data.datasource.remote

import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.data.api.PropertyListingApi
import au.com.deanpike.data.model.external.AddressComponents
import au.com.deanpike.data.model.external.Advertiser
import au.com.deanpike.data.model.external.AgencyListingContact
import au.com.deanpike.data.model.external.GeoLocation
import au.com.deanpike.data.model.external.Images
import au.com.deanpike.data.model.external.ListingResponse
import au.com.deanpike.data.model.external.Medium
import au.com.deanpike.data.model.external.MetaData
import au.com.deanpike.data.model.external.SearchResult
import au.com.deanpike.data.model.internal.ListingSearchRequest
import au.com.deanpike.datashared.util.ResponseWrapper
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListingDataSourceImplTest {
    private val api: PropertyListingApi = mockk()
    private lateinit var dataSource: ListingDataSource
    private val now = DateTime.now()
    private val dateFormat = "YYYY-MM-dd'T'HH:mm:ssZ"

    @BeforeEach
    fun setupTest() {
        dataSource = ListingDataSourceImpl(
            api = api
        )
    }

    @Test
    fun `should get listings`() = runTest {
        coEvery {
            api.getListings(
                contentType = "application/json",
                body = ListingSearchRequest(
                    searchMode = "Buy",
                    dwellingTypes = listOf("House")
                )
            )
        } returns getResponse()

        val response = dataSource.getListings(
            request = ListingSearchRequest(
                searchMode = "Buy",
                dwellingTypes = listOf("House")
            )
        )

        assertThat(response).isInstanceOf(ResponseWrapper::class.java)
        val data = (response as ResponseWrapper.Success).data

        with(data) {
            assertThat(newResults).isNull()
            assertThat(resultsTotal).isEqualTo(31000)
            assertThat(resultsReturned).isEqualTo(201)
            assertThat(schoolMetadata).isNull()
            assertThat(allowsImmediatePropertyAlert).isFalse()
            assertThat(searchResults.size).isEqualTo(1)
        }

        with(data.searchResults[0]) {
            assertThat(listingType).isEqualTo(ListingType.PROPERTY)
            assertThat(id).isEqualTo(1)
            assertThat(dateListed).isEqualTo(now.toString(dateFormat))
            assertThat(address).isEqualTo("104/86 Burke Road, Malvern East")
            assertThat(price).isEqualTo("$1,549,000")
            assertThat(media[0].imageUrl).isEqualTo("http://some.url.com")
            assertThat(bedroomCount).isEqualTo(4)
            assertThat(bathroomCount).isEqualTo(2)
            assertThat(homepassEnabled).isTrue()
            assertThat(additionalFeatures!![0]).isEqualTo("Feature 1")
            assertThat(geoLocation.latitude).isEqualTo(-37.8696938)
            assertThat(geoLocation.longitude).isEqualTo(145.0498)
            assertThat(promoLevel).isEqualTo("promo_level_1")
            assertThat(carspaceCount).isEqualTo(2)
            assertThat(dwellingType).isEqualTo("house")
            assertThat(hasVideo).isFalse()
            assertThat(headline).isEqualTo("Central Park Luxury")
            assertThat(landArea).isEqualTo("150m²")
            assertThat(largeLand).isFalse()
            assertThat(metadata!!.addressComponents.unitNumber).isEqualTo("1")
            assertThat(metadata.addressComponents.street).isEqualTo("Burke Road")
            assertThat(advertiser!!.name).isEqualTo("Marshall White Stonnington")
            assertThat(topspot).isNull()
            assertThat(project).isNull()
            assertThat(lifecycleStatus).isEqualTo("Status")
            assertThat(earliestInspections!!.isEmpty()).isTrue()
            assertThat(auctionDate).isEqualTo(now.plusDays(3).toString(dateFormat))
        }
    }

    @Test
    fun `should handle exception`() = runTest {
        coEvery {
            api.getListings(
                contentType = "application/json",
                body = ListingSearchRequest(
                    searchMode = "Buy",
                    dwellingTypes = listOf("House")
                )
            )
        } throws IOException("No internet")

        val response = dataSource.getListings(
            request = ListingSearchRequest(
                searchMode = "Buy",
                dwellingTypes = listOf("House")
            )
        )

        assertThat(response).isInstanceOf(ResponseWrapper.Error::class.java)
        val error = response as ResponseWrapper.Error
        assertThat(error.exception).isInstanceOf(IOException::class.java)
        assertThat(error.exception.message).isEqualTo("No internet")
    }

    private fun getResponse(): ListingResponse {
        val media = Medium(type = "photo", imageUrl = "http://some.url.com", mediaType = "image")
        val geoLocation = GeoLocation(-37.8696938, 145.0498)
        val listingContact = AgencyListingContact(displayFullName = "Daniel Wheeler", imageUrl = "http://some.image.url")
        val advertiser = Advertiser(
            agencyListingContacts = listOf(listingContact),
            id = 4450,
            images = Images(logoUrl = "http://some.logo.url"),
            name = "Marshall White Stonnington",
            preferredColorHex = "#09142b"
        )
        val metaData = MetaData(
            AddressComponents(
                unitNumber = "1",
                street = "Burke Road",
                streetNumber = "104/86",
                stateShort = "VIC",
                suburbId = 25077,
                suburb = "MALVERN EAST",
                postcode = "3145"
            )
        )

        val searchResult = SearchResult(
            listingType = ListingType.PROPERTY,
            id = 1,
            dateListed = now.toString(dateFormat),
            address = "104/86 Burke Road, Malvern East",
            price = "$1,549,000",
            media = listOf(media),
            bedroomCount = 4,
            bathroomCount = 2,
            homepassEnabled = true,
            additionalFeatures = listOf("Feature 1"),
            geoLocation = geoLocation,
            promoLevel = "promo_level_1",
            carspaceCount = 2,
            dwellingType = "house",
            hasVideo = false,
            headline = "Central Park Luxury",
            landArea = "150m²",
            largeLand = false,
            metadata = metaData,
            advertiser = advertiser,
            topspot = null,
            project = null,
            lifecycleStatus = "Status",
            earliestInspections = emptyList(),
            auctionDate = now.plusDays(3).toString(dateFormat),
        )

        return ListingResponse(
            searchResults = listOf(searchResult),
            newResults = null,
            resultsTotal = 31000,
            resultsReturned = 201,
            schoolMetadata = null,
            allowsImmediatePropertyAlert = false,
        )
    }
}