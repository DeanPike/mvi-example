package au.com.deanpike.listings.data.api

import au.com.deanpike.network.api.ListingApi
import au.com.deanpike.network.model.internal.ListingSearchRequest
import com.google.gson.GsonBuilder
import java.io.InputStreamReader
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropertyListingApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: ListingApi
    private lateinit var jsonResponse: String
    private val gson = GsonBuilder()
        .create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ListingApi::class.java)
        jsonResponse = readFile()
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `get listings`() = runTest {
        val res = MockResponse()
        res.setBody(jsonResponse)
        server.enqueue(res)

        val data = api.getListings(
            contentType = "application/json",
            body = ListingSearchRequest(
                searchMode = "buy",
                dwellingTypes = listOf("Apartment / Unit / Flat")
            )
        )
        server.takeRequest()//let the server take the request

        assertThat(data.resultsReturned).isEqualTo(201)
        assertThat(data.searchResults.size).isEqualTo(3)

        // Top Spot
        with(data.searchResults[0]) {
            assertThat(listingType).isEqualTo("topspot")
            assertThat(id).isEqualTo(2019096805)
            assertThat(dateListed).isEqualTo("2024-03-05T16:43:35+11:00")
            assertThat(address).isEqualTo("41 Chifley Road, Lithgow")
            assertThat(price).isEqualTo("$425,000")
            assertThat(media!!.size).isEqualTo(4)
            assertThat(media!![0].imageUrl).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2019096805_1_1_240305_054335-w2048-h1365")
            assertThat(media!![3].imageUrl).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2019096805_4_1_240305_054334-w1754-h1240")
            assertThat(bedroomCount).isEqualTo(3)
            assertThat(bathroomCount).isEqualTo(1)
            assertThat(homepassEnabled).isFalse()
            assertThat(geoLocation.latitude).isEqualTo(-33.478928)
            assertThat(geoLocation.longitude).isEqualTo(150.16753)
            assertThat(promoLevel).isEqualTo("StandardPP")
            assertThat(carspaceCount).isEqualTo(1)
            assertThat(dwellingType).isEqualTo("House")
            assertThat(hasVideo).isFalse()
            assertThat(headline).isEqualTo("Full Brick Close to Town")
            assertThat(landArea).isEqualTo("382m²")
            assertThat(largeLand).isFalse()
            assertThat(metadata!!.addressComponents.street).isEqualTo("Chifley Road")
            assertThat(metadata!!.addressComponents.streetNumber).isEqualTo("41")
            assertThat(metadata!!.addressComponents.stateShort).isEqualTo("NSW")
            assertThat(metadata!!.addressComponents.suburbId).isEqualTo(25842)
            assertThat(metadata!!.addressComponents.suburb).isEqualTo("LITHGOW")
            assertThat(metadata!!.addressComponents.postcode).isEqualTo("2790")
            assertThat(advertiser!!.agencyListingContacts.size).isEqualTo(1)
            assertThat(advertiser!!.agencyListingContacts[0].displayFullName).isEqualTo("Blake Edgell")
            assertThat(advertiser!!.id).isEqualTo(17114)
            assertThat(advertiser!!.images.logoUrl).isEqualTo("https://images.domain.com.au/img/Agencys/17114/logo_17114.png?buster=2024-04-01")
            assertThat(advertiser!!.name).isEqualTo("LJ Hooker Lithgow")
            assertThat(advertiser!!.preferredColorHex).isEqualTo("#ffffff")
            assertThat(lifecycleStatus).isEqualTo("Under Offer")
            assertThat(topspot!!.availableListings).isEqualTo(30)
        }

        // Project
        with(data.searchResults[1]) {
            assertThat(listingType).isEqualTo("project")
            assertThat(id).isEqualTo(2842)
            assertThat(address).isEqualTo("81 KITTYHAWK DRIVE, CHERMSIDE, QLD 4032")
            assertThat(promoLevel).isEqualTo("P+")
            assertThat(project!!.projectName).isEqualTo("Estilo on the Park")

            assertThat(media!!.size).isEqualTo(1)
            assertThat(media!![0].type).isEqualTo("photo")
            assertThat(media!![0].imageUrl).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2842_8_13_210413_030454-w3244-h2160")
            assertThat(media!![0].mediaType).isEqualTo("image")
            assertThat(geoLocation.latitude).isEqualTo(-27.381521)
            assertThat(geoLocation.longitude).isEqualTo(153.03517)
        }
        with(data.searchResults[1].project!!) {
            assertThat(projectBannerImage).isEqualTo("https://images.domain.com.au/img/Agencys/devproject/banner_2842_240304_023807")
            assertThat(projectLogoImage).isEqualTo("https://images.domain.com.au/img/Agencys/devproject/logo_2842_240304_023807")
            assertThat(projectPriceFrom).isEqualTo(869000)
            assertThat(childListings.size).isEqualTo(2)
            assertThat(projectColorHex).isEqualTo("#00000d")

            assertThat(childListings[0].id).isEqualTo(2019090910)
            assertThat(childListings[0].dateListed).isEqualTo("2024-03-01T16:33:13+11:00")
            assertThat(childListings[0].lifecycleStatus).isEqualTo("New Home")
            assertThat(childListings[0].price).isEqualTo("$3,250,000")
            assertThat(childListings[0].bathroomCount).isEqualTo(3)
            assertThat(childListings[0].bedroomCount).isEqualTo(4)
            assertThat(childListings[0].carspaceCount).isEqualTo(2)
            assertThat(childListings[0].homepassEnabled).isFalse()
            assertThat(childListings[0].propertySize).isEqualTo("283m²")
            assertThat(childListings[0].earliestInspections!!.size).isEqualTo(2)
            assertThat(childListings[0].earliestInspections!![0].timeOpen).isEqualTo("2024-04-03T10:00:00")
            assertThat(childListings[0].earliestInspections!![0].timeClose).isEqualTo("2024-04-03T14:00:00")
            assertThat(childListings[0].earliestInspections!![0].recurrenceType).isEqualTo("weekly")
            assertThat(childListings[0].earliestInspections!![1].timeOpen).isEqualTo("2024-04-04T10:00:00")
            assertThat(childListings[0].earliestInspections!![1].timeClose).isEqualTo("2024-04-04T14:00:00")
            assertThat(childListings[0].earliestInspections!![1].recurrenceType).isEqualTo("weekly")

            assertThat(childListings[1].id).isEqualTo(2019090988)
            assertThat(childListings[1].dateListed).isEqualTo("2024-03-01T16:51:27+11:00")
            assertThat(childListings[1].lifecycleStatus).isEqualTo("New Home")
            assertThat(childListings[1].price).isEqualTo("$1,999,000")
            assertThat(childListings[1].bathroomCount).isEqualTo(3)
            assertThat(childListings[1].bedroomCount).isEqualTo(4)
            assertThat(childListings[1].carspaceCount).isEqualTo(2)
            assertThat(childListings[1].homepassEnabled).isFalse()
            assertThat(childListings[1].propertySize).isEqualTo("196m²")
            assertThat(childListings[1].earliestInspections!!.size).isEqualTo(2)
            assertThat(childListings[1].earliestInspections!![0].timeOpen).isEqualTo("2024-04-03T10:00:00")
            assertThat(childListings[1].earliestInspections!![0].timeClose).isEqualTo("2024-04-03T14:00:00")
            assertThat(childListings[1].earliestInspections!![0].recurrenceType).isEqualTo("weekly")
            assertThat(childListings[1].earliestInspections!![1].timeOpen).isEqualTo("2024-04-04T10:00:00")
            assertThat(childListings[1].earliestInspections!![1].timeClose).isEqualTo("2024-04-04T14:00:00")
            assertThat(childListings[1].earliestInspections!![1].recurrenceType).isEqualTo("weekly")
        }

        // Property
        with(data.searchResults[2]) {
            assertThat(listingType).isEqualTo("property")
            assertThat(id).isEqualTo(2019150933)
            assertThat(dateListed).isEqualTo("2024-04-01T10:43:10+11:00")
            assertThat(address).isEqualTo("14 Mayfair Drive, Browns Plains")
            assertThat(price).isEqualTo("Auction")
            assertThat(media!!.size).isEqualTo(3)
            assertThat(media!![0].type).isEqualTo("photo")
            assertThat(media!![0].imageUrl).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2019150933_1_1_240331_114308-w3600-h2400")
            assertThat(media!![0].mediaType).isEqualTo("image")
            assertThat(media!![1].type).isEqualTo("photo")
            assertThat(media!![1].imageUrl).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2019150933_2_1_240331_114308-w3600-h2400")
            assertThat(media!![1].mediaType).isEqualTo("image")
            assertThat(media!![2].type).isEqualTo("photo")
            assertThat(media!![2].imageUrl).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2019150933_3_1_240331_114308-w3600-h2400")
            assertThat(media!![2].mediaType).isEqualTo("image")
            assertThat(bedroomCount).isEqualTo(3)
            assertThat(bathroomCount).isEqualTo(1)
            assertThat(homepassEnabled).isFalse()
            assertThat(additionalFeatures!!.size).isEqualTo(1)
            assertThat(additionalFeatures!![0]).isEqualTo("internal laundry")
            assertThat(geoLocation.latitude).isEqualTo(-27.66721)
            assertThat(geoLocation.longitude).isEqualTo(153.05362)
            assertThat(promoLevel).isEqualTo("P+")
            assertThat(carspaceCount).isEqualTo(2)
            assertThat(dwellingType).isEqualTo("House")
            assertThat(hasVideo).isFalse()
            assertThat(headline).isEqualTo("AUCTION IN-ROOMS 30TH APRIL 2024 AT 6PM")
            assertThat(landArea).isEqualTo("780m²")
            assertThat(largeLand).isFalse()
            assertThat(metadata!!.addressComponents.street).isEqualTo("Mayfair Drive")
            assertThat(metadata!!.addressComponents.streetNumber).isEqualTo("14")
            assertThat(metadata!!.addressComponents.stateShort).isEqualTo("QLD")
            assertThat(metadata!!.addressComponents.suburbId).isEqualTo(4974)
            assertThat(metadata!!.addressComponents.suburb).isEqualTo("BROWNS PLAINS")
            assertThat(metadata!!.addressComponents.postcode).isEqualTo("4118")
            assertThat(advertiser!!.agencyListingContacts.size).isEqualTo(1)
            assertThat(advertiser!!.agencyListingContacts[0].displayFullName).isEqualTo("Tammie Lor")
            assertThat(advertiser!!.agencyListingContacts[0].imageUrl).isEqualTo("https://images.domain.com.au/img/30838/contact_1613068.jpeg?buster=2024-04-01")
            assertThat(advertiser!!.id).isEqualTo(30838)
            assertThat(advertiser!!.images.logoUrl).isEqualTo("https://images.domain.com.au/img/Agencys/30838/logo_30838.jpg?buster=2024-04-01")
            assertThat(advertiser!!.name).isEqualTo("Ray White Marsden")
            assertThat(advertiser!!.preferredColorHex).isEqualTo("#FEE536")
            assertThat(lifecycleStatus).isEqualTo("New")
            assertThat(auctionDate).isEqualTo("2024-04-30T18:00:00")
            assertThat(earliestInspections!!.size).isEqualTo(1)
            assertThat(earliestInspections!![0].timeOpen).isEqualTo("2024-04-06T11:15:00")
            assertThat(earliestInspections!![0].timeClose).isEqualTo("2024-04-06T11:45:00")
            assertThat(earliestInspections!![0].recurrenceType).isEqualTo("none")
        }

        assertThat(data.newResults).isNull()
        assertThat(data.resultsTotal).isEqualTo(166308)
        assertThat(data.resultsReturned).isEqualTo(201)
        assertThat(data.schoolMetadata).isNull()
        assertThat(data.allowsImmediatePropertyAlert).isTrue()
    }

    private fun readFile(): String {
        return ClassLoader.getSystemResourceAsStream("raw/listing_response.json")?.let { InputStreamReader(it, "UTF-8").readText() }!!
    }
}