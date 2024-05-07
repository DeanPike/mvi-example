package au.com.deanpike.ui.e2e

import au.com.deanpike.client.model.listing.response.ListingDetails
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.model.listing.response.Project
import au.com.deanpike.client.model.listing.response.ProjectChild
import au.com.deanpike.client.model.listing.response.Property
import au.com.deanpike.ui.framework.screen.ProjectListItemScreen
import au.com.deanpike.ui.framework.screen.PropertyListItemScreen
import au.com.deanpike.ui.screen.list.ListingListScreen
import au.com.deanpike.ui.screen.list.ListingListScreenTestTags
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiE2ETestBase
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.waitUntilTagExists
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import org.junit.Test

@HiltAndroidTest
class BuyListingTest : UiE2ETestBase() {

    private val propertyListScreen = PropertyListItemScreen(composeTestRule)
    private val projectListScreen = ProjectListItemScreen(composeTestRule)

    @Test
    fun test_buy_listing_flow() {
        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/search",
            filename = "raw/listing_response.json",
            httpMethod = "POST",
            status = HttpURLConnection.HTTP_OK
        )

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreen()
                }
            }
            advanceTimeAndWait()

            waitUntilTagExists(tag = ListingListScreenTestTags.LISTING_LIST, timeout = 2000)
            assertTagDisplayed(ListingListScreenTestTags.LISTING_LIST)
            assertTextDisplayed(tag = ListingListScreenTestTags.LISTING_LIST_HEADING, text = "2 Properties")

            projectListScreen.assertProjectDisplayed(
                position = 0,
                project = Project(
                    id = 2842,
                    listingType = ListingType.PROJECT,
                    address = "81 KITTYHAWK DRIVE, CHERMSIDE, QLD 4032",
                    listingImage = "https://bucket-api.domain.com.au/v1/bucket/image/2842_8_13_210413_030454-w3244-h2160",
                    bannerImage = "https://images.domain.com.au/img/Agencys/devproject/banner_2842_240304_023807",
                    logoImage = "https://images.domain.com.au/img/Agencys/devproject/logo_2842_240304_023807",
                    projectName = "Estilo on the Park",
                    projectColour = "#00000d",
                    properties = listOf(
                        ProjectChild(
                            id = 2019090910,
                            listingType = ListingType.PROPERTY,
                            lifecycleStatus = "New Home",
                            listingDetails = ListingDetails(
                                price = "$3,250,000",
                                numberOfBedrooms = 4,
                                numberOfBathrooms = 3,
                                numberOfCarSpaces = 2
                            )
                        ),
                        ProjectChild(
                            id = 2019090988,
                            listingType = ListingType.PROPERTY,
                            lifecycleStatus = "New",
                            listingDetails = ListingDetails(
                                price = "$1,999,000",
                                numberOfBedrooms = 4,
                                numberOfBathrooms = 3,
                                numberOfCarSpaces = 2
                            )
                        )
                    )
                )
            )

            propertyListScreen.assertPropertyDisplayed(
                position = 1,
                property = Property(
                    id = 2019150933,
                    listingType = ListingType.PROPERTY,
                    address = "14 Mayfair Drive, Browns Plains",
                    listingImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019150933_1_1_240331_114308-w3600-h2400",
                    agencyImage = "https://images.domain.com.au/img/Agencys/30838/logo_30838.jpg?buster=2024-04-01",
                    agencyColour = "#FEE536",
                    dwellingType = "House",
                    headLine = "AUCTION IN-ROOMS 30TH APRIL 2024 AT 6PM",
                    lifecycleStatus = "New",
                    detail = ListingDetails(
                        price = "Auction",
                        numberOfBedrooms = 3,
                        numberOfBathrooms = 1,
                        numberOfCarSpaces = 2
                    )
                )
            )
        }
    }
}