package au.com.deanpike.data.repository

import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.data.converter.ListingConverterFactory
import au.com.deanpike.data.converter.PropertyConverter
import au.com.deanpike.data.datasource.remote.ListingDataSource
import au.com.deanpike.data.model.external.Advertiser
import au.com.deanpike.data.model.external.GeoLocation
import au.com.deanpike.data.model.external.Images
import au.com.deanpike.data.model.external.ListingResponse
import au.com.deanpike.data.model.external.Medium
import au.com.deanpike.data.model.external.SearchResult
import au.com.deanpike.data.model.internal.ListingSearchRequest
import au.com.deanpike.client.util.ResponseWrapper
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ListingRepositoryImplTest {
    private val dataSource: ListingDataSource = mockk()
    private val converterFactory: ListingConverterFactory = mockk()

    private lateinit var repo: ListingRepository

    @BeforeEach
    fun setupTest() {
        repo = ListingRepositoryImpl(
            dataSource = dataSource,
            converterFactory = converterFactory
        )
    }

    @Test
    fun `should convert listing`() = runTest {
        coEvery {
            dataSource.getListings(
                request = ListingSearchRequest(
                    searchMode = "Buy",
                    dwellingTypes = listOf("House")
                )
            )
        } returns ResponseWrapper.Success(
            ListingResponse(
                newResults = 0,
                resultsTotal = 1000,
                resultsReturned = 200,
                allowsImmediatePropertyAlert = true,
                schoolMetadata = null,
                searchResults = listOf(
                    getProperty()
                )
            )
        )

        coEvery { converterFactory.getConverter(ListingType.PROPERTY) } returns PropertyConverter()

        val listings = repo.getListings(
            request = ListingSearchRequest(
                searchMode = "Buy",
                dwellingTypes = listOf("House")
            )
        )

        assertThat(listings).isInstanceOf(ResponseWrapper.Success::class.java)
        val success = listings as ResponseWrapper.Success
        assertThat(success.data.size).isEqualTo(1)
        assertThat(success.data[0]).isInstanceOf(Property::class.java)

        with(success.data[0] as Property) {
            assertThat(id).isEqualTo(1234)
            assertThat(listingType).isEqualTo(ListingType.PROPERTY)
            assertThat(address).isEqualTo("Property address")
            assertThat(listingImage).isEqualTo("http://some.image.one")
            assertThat(agencyImage).isEqualTo("http://advertiser.logo")
            assertThat(dwellingType).isEqualTo("House")
            assertThat(headLine).isEqualTo("Property Headline")
            assertThat(lifecycleStatus).isEqualTo("New")
            assertThat(detail.price).isEqualTo("1500000")
            assertThat(detail.numberOfBedrooms).isEqualTo(4)
            assertThat(detail.numberOfBathrooms).isEqualTo(3)
            assertThat(detail.numberOfCarSpaces).isEqualTo(2)
        }
    }

    @Test
    fun `should handle error`() = runTest {
        coEvery {
            dataSource.getListings(
                request = ListingSearchRequest(
                    searchMode = "Buy",
                    dwellingTypes = listOf("House")
                )
            )
        } returns ResponseWrapper.Error(IOException("No internet"))

        coEvery { converterFactory.getConverter(ListingType.PROPERTY) } returns PropertyConverter()

        val listings = repo.getListings(
            request = ListingSearchRequest(
                searchMode = "Buy",
                dwellingTypes = listOf("House")
            )
        )

        assertThat(listings).isInstanceOf(ResponseWrapper.Error::class.java)
        val error = listings as ResponseWrapper.Error
        assertThat(error.exception).isInstanceOf(IOException::class.java)
        assertThat(error.exception.message).isEqualTo("No internet")
    }

    private fun getProperty(): SearchResult {
        return SearchResult(
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
    }
}