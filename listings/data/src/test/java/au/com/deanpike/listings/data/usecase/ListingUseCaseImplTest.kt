package au.com.deanpike.listings.data.usecase

import au.com.deanpike.commonshared.model.ListingDetails
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.GeoLocation
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.client.model.listing.search.ListingSearch
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.listings.client.type.DwellingType.HOUSE
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.client.usecase.ListingUseCase
import au.com.deanpike.listings.data.repository.ListingRepository
import au.com.deanpike.network.model.internal.AddressComponents
import au.com.deanpike.network.model.internal.ListingSearchRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListingUseCaseImplTest {
    private val repo: ListingRepository = mockk()
    private lateinit var useCase: ListingUseCase

    @BeforeEach
    fun setupTest() {
        useCase = ListingUseCaseImpl(
            repo = repo
        )
    }

    @Test
    fun `get listings`() = runTest {
        val property = getProperty()

        coEvery {
            repo.getListings(
                request = ListingSearchRequest(
                    searchMode = "buy",
                    dwellingTypes = listOf("House")
                )
            )
        } returns ResponseWrapper.Success(
            listOf(
                property
            )
        )

        val listingsResponse = useCase.getListings(
            ListingSearch(
                searchMode = StatusType.BUY,
                dwellingTypes = listOf(HOUSE)
            )
        )

        assertThat(listingsResponse).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (listingsResponse as ResponseWrapper.Success).data
        assertThat(data.size).isEqualTo(1)
        assertThat(data[0]).isEqualTo(property)
    }

    @Test
    fun `get listings with location`() = runTest {
        val property = getProperty()

        coEvery {
            repo.getListings(
                request = ListingSearchRequest(
                    searchMode = "buy",
                    dwellingTypes = listOf("House"),
                    location = listOf(
                        AddressComponents(
                            area = "Eastern Suburbs",
                            postcode = "2026",
                            region = "Sydney",
                            stateShort = "NSW",
                            suburb = "Bondi Beach",
                            suburbId = 12345
                        )
                    )
                )
            )
        } returns ResponseWrapper.Success(
            listOf(
                property
            )
        )

        val listingsResponse = useCase.getListings(
            ListingSearch(
                searchMode = StatusType.BUY,
                dwellingTypes = listOf(HOUSE),
                location = Location(
                    displayName = "Bondi Beach, NSW 2026",
                    name = "Bondi Beach",
                    state = "NSW",
                    regionName = "Sydney",
                    areaName = "Eastern Suburbs",
                    postCode = "2026",
                    suburbId = "12345"
                )
            )
        )

        assertThat(listingsResponse).isInstanceOf(ResponseWrapper.Success::class.java)
    }

    private fun getProperty() = Property(
        id = 1234,
        listingType = ListingType.PROPERTY,
        address = "Address",
        listingImage = "http://listing.image",
        agencyImage = "http://agency.image",
        agencyColour = "Red",
        dwellingType = "House",
        headLine = "Headline",
        lifecycleStatus = "New",
        detail = ListingDetails(
            price = "1234000",
            numberOfBedrooms = 4,
            numberOfBathrooms = 2,
            numberOfCarSpaces = 1
        ),
        geoLocation = GeoLocation(
            latitude = 1.1,
            longitude = 2.2
        )
    )
}