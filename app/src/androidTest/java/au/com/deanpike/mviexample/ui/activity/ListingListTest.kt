package au.com.deanpike.mviexample.ui.activity

import au.com.deanpike.listings.ui.framework.robot.ListingListScreenRobot
import au.com.deanpike.listings.ui.framework.robot.ProjectChildListItemComponentRobot
import au.com.deanpike.listings.ui.framework.robot.ProjectListItemRobot
import au.com.deanpike.listings.ui.framework.robot.PropertyListItemRobot
import au.com.deanpike.listings.ui.list.ListingListScreen
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_LIST
import au.com.deanpike.mviexample.ui.util.HiltE2ETestActivity
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiE2ETestBase
import au.com.deanpike.uitestshared.mockserver.HttpMethod
import au.com.deanpike.uitestshared.mockserver.MockRequestHandler
import au.com.deanpike.uitestshared.robot.AgencyBannerComponentRobot
import au.com.deanpike.uitestshared.robot.ErrorComponentRobot
import au.com.deanpike.uitestshared.robot.LifecycleStatusComponentRobot
import au.com.deanpike.uitestshared.robot.PropertyDetailComponentRobot
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ListingListTest : UiE2ETestBase() {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRuleFor(HiltE2ETestActivity::class.java)

    private val listingListScreenRobot = ListingListScreenRobot(composeTestRule)
    private val propertyRobot = PropertyListItemRobot(composeTestRule)
    private val propertyDetailRobot = PropertyDetailComponentRobot(composeTestRule)
    private val projectRobot = ProjectListItemRobot(composeTestRule)
    private val projectChildRobot = ProjectChildListItemComponentRobot(composeTestRule)
    private val errorRobot = ErrorComponentRobot(composeTestRule)
    private val agencyBannerRobot = AgencyBannerComponentRobot(composeTestRule)
    private val lifecycleRobot = LifecycleStatusComponentRobot(composeTestRule)

    @Test
    fun test_buy_listing_flow() {
        var propertyClicked: Long? = null
        var projectClicked: Long? = null
        var projectChildClicked: Long? = null

        setupResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreen(
                        onPropertyClicked = {
                            propertyClicked = it
                        },
                        onProjectClicked = {
                            projectClicked = it
                        },
                        onProjectChildClicked = { projectId, childId ->
                            projectClicked = projectId
                            projectChildClicked = childId
                        }
                    )
                }
            }
            listingListScreenRobot
                .waitUntilListShown()
                .assertHeaderText("2 Properties")

            // Project
            projectRobot
                .assertLayoutDisplayed()
                .assertBannerImageDisplayed()
                .assertProjectImageDisplayed()
                .assertProjectName("Estilo on the Park")
                .assertAddress("81 KITTYHAWK DRIVE, CHERMSIDE, QLD 4032")
                .assertProjectChildrenButtonText("2 Properties")

            agencyBannerRobot
                .assertImageDisplayedAtPosition(
                    parentTag = LISTING_LIST,
                    position = 0
                )

            projectRobot
                .clickProjectChildButton()
                .assertChildrenDisplayed()

            projectChildRobot
                .forChild(2019256252)
                .assertChildLayoutDisplayed()
                .assertPriceDisplayed(text = "$3,250,000")
                .assertLifecycleDisplayed(text = "New Home")
                .assertBedroomDisplayed(bedrooms = "4")
                .assertBathroomDisplayed(bathrooms = "3")
                .assertCarSpacesDisplayed(carSpaces = "2")
                .clickCard()
            assertThat(projectChildClicked).isEqualTo(2019256252)

            projectChildRobot
                .forChild(2019090988)
                .assertChildLayoutDisplayed()
                .assertPriceDisplayed(text = "$1,999,000")
                .assertLifecycleDisplayed(text = "New")
                .assertBedroomDisplayed(bedrooms = "5")
                .assertBathroomDisplayed(bathrooms = "2")
                .assertCarSpacesDisplayed(carSpaces = "1")
                .clickCard()
            assertThat(projectChildClicked).isEqualTo(2019090988)

            projectRobot.clickProject()
            assertThat(projectClicked).isEqualTo(2842)

            // Property
            listingListScreenRobot.scrollToPosition(1)
            lifecycleRobot.assertLifecycle("New")
            propertyRobot
                .assertLayoutDisplayed()
                .assertPropertyImageDisplayed()
                .assertPriceDisplayed("Auction")
                .assertAddressDisplayed("14 Mayfair Drive, Browns Plains")
                .assertBedroomDisplayed("3")
                .assertBathroomDisplayed("1")
                .assertCarSpacesDisplayed("2")
                .assertHeadlineDisplayed("AUCTION IN-ROOMS 30TH APRIL 2024 AT 6PM")
                .clickProperty()
            assertThat(propertyClicked).isEqualTo(2019150933)

            agencyBannerRobot
                .assertImageDisplayedAtPosition(
                    parentTag = LISTING_LIST,
                    position = 1
                )

            propertyDetailRobot.assertDwellingTypeDisplayed("House")
        }
    }

    @Test
    fun test_error_flow() {
        setupErrorResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ListingListScreen()
                }
            }

            errorRobot.waitUntilListShown()

            setupResponse(
                listingType = emptyList(),
                statusType = "buy"
            )

            errorRobot.clickRetryButton()

            listingListScreenRobot
                .waitUntilListShown()
                .assertListDisplayed()
                .assertHeaderText("2 Properties")
        }
    }

    private fun setupErrorResponse(listingType: List<String>, statusType: String) {
        val listingTypes = listingType.map {
            "\"$it\""
        }.toString()

        webServerDispatcher.addResponse(
            pathPattern = "v1/search",
            httpMethod = HttpMethod.POST,
            body = """{"dwelling_types":$listingTypes,"search_mode":"$statusType"}""",
            requestHandler = object : MockRequestHandler {
                override fun invoke(request: RecordedRequest): MockResponse {
                    return MockResponse().apply {
                        setBody("""{"error":"Bad url"}""")
                        setResponseCode(404)
                    }
                }
            }
        )
    }

    private fun setupResponse(listingType: List<String>, statusType: String) {

        val listingTypes = listingType.map {
            "\"$it\""
        }.toString()

        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/search",
            filename = "raw/listing_response.json",
            httpMethod = HttpMethod.POST,
            status = HttpURLConnection.HTTP_OK,
            body = """{"dwelling_types":$listingTypes,"search_mode":"$statusType"}"""
        )
    }
}