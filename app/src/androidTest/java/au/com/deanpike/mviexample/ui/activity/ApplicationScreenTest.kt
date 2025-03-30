package au.com.deanpike.mviexample.ui.activity

import au.com.deanpike.detail.ui.framework.robot.ProjectChildrenComponentRobot
import au.com.deanpike.detail.ui.framework.robot.ProjectDetailScreenRobot
import au.com.deanpike.detail.ui.framework.robot.PropertyDetailScreenRobot
import au.com.deanpike.detail.ui.project.ProjectDetailScreenTestTags
import au.com.deanpike.listings.ui.framework.robot.ListingListScreenRobot
import au.com.deanpike.listings.ui.framework.robot.ProjectChildListItemComponentRobot
import au.com.deanpike.listings.ui.framework.robot.ProjectListItemRobot
import au.com.deanpike.listings.ui.framework.robot.PropertyListItemRobot
import au.com.deanpike.mviexample.ui.util.HiltE2ETestActivity
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.UiE2ETestBase
import au.com.deanpike.uitestshared.mockserver.HttpMethod
import au.com.deanpike.uitestshared.robot.ToolbarComponentRobot
import au.com.deanpike.uitestshared.util.advanceTimeAndWait
import au.com.deanpike.uitestshared.util.waitUntilTagExists
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ApplicationScreenTest : UiE2ETestBase() {
    @get:Rule(order = 1)
    val composeTestRule = createComposeRuleFor(HiltE2ETestActivity::class.java)

    private val listingListScreenRobot = ListingListScreenRobot(composeTestRule)
    private val projectListItemRobot = ProjectListItemRobot(composeTestRule)
    private val projectRobot = ProjectDetailScreenRobot(composeTestRule)
    private var projectChildrenRobot = ProjectChildrenComponentRobot(composeTestRule)
    private val projectChildListItemRobot = ProjectChildListItemComponentRobot(composeTestRule)
    private val propertyListItemRobot = PropertyListItemRobot(composeTestRule)
    private var propertyRobot = PropertyDetailScreenRobot(composeTestRule)
    private val toolbarRobot = ToolbarComponentRobot(composeTestRule)


    @Test
    fun show_project_child_via_project_details() = runTest {
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )

        setupProjectResponse()

        setupProjectChildDetailResponse()

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ApplicationScreen()
                }
            }
            waitForIdle()

            listingListScreenRobot
                .waitUntilListShown()

            projectListItemRobot
                .assertLayoutDisplayed()
                .assertProjectName("Estilo on the Park")
                .clickProject()

            composeTestRule.waitUntilTagExists(tag = ProjectDetailScreenTestTags.PROJECT_DETAIL_NAME)

            // Project
            projectRobot
                .waitUntilScreenDisplayed()
                .assertLayoutDisplayed()
                .assertProjectName("Easterly Wollongong")

            projectChildrenRobot
                .clickOnChild(0)

            waitForIdle()

            // Project detail
            propertyRobot
                .waitForSuccessScreenToBeDisplayed()
                .assertDescriptionDisplayed()
            toolbarRobot.clickBack()
            waitForIdle()

            // Navigate back to the project
            projectRobot
                .waitUntilScreenDisplayed()
                .assertLayoutDisplayed()
                .assertProjectName("Easterly Wollongong")
                .clickOnClose()
            advanceTimeAndWait()

            setupListingResponse(
                listingType = emptyList(),
                statusType = "buy"
            )

            listingListScreenRobot
                .waitUntilListShown()
        }
    }

    @Test
    fun show_project_child_via_project_on_list_screen() {
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )
        setupProjectChildDetailResponse()

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ApplicationScreen()
                }
            }
            listingListScreenRobot
                .waitUntilListShown()

            // Conform the project is visible
            projectListItemRobot
                .assertLayoutDisplayed()
                .assertProjectName("Estilo on the Park")
                .clickProjectChildButton()
                .assertChildrenDisplayed()

            // Confirm the project child is visible on the list screen
            projectChildListItemRobot
                .forChild(2019256252)
                .assertChildLayoutDisplayed()
                .clickCard()
            advanceTimeAndWait()

            // Show the project detail
            propertyRobot
                .waitForSuccessScreenToBeDisplayed()
                .assertDescriptionDisplayed()
                .swipeBack()
            advanceTimeAndWait()

            setupListingResponse(
                listingType = emptyList(),
                statusType = "buy"
            )

            // Show the list screen
            listingListScreenRobot
                .waitUntilListShown()
        }
    }

    @Test
    fun show_property_details_screen() {
        setupListingResponse(
            listingType = emptyList(),
            statusType = "buy"
        )
        setupPropertyDetailResponse()

        with(composeTestRule) {
            setContent {
                MviExampleTheme {
                    ApplicationScreen()
                }
            }
            advanceTimeAndWait()

            listingListScreenRobot
                .waitUntilListShown()
                .scrollToPosition(1)

            propertyListItemRobot
                .waitForLayoutToBeDisplayed()
                .assertAddressDisplayed("14 Mayfair Drive, Browns Plains")
                .clickProperty()
            advanceTimeAndWait()

            // Property
            propertyRobot
                .waitForSuccessScreenToBeDisplayed()
                .assertDescriptionDisplayed()
                .swipeBack()

            setupListingResponse(
                listingType = emptyList(),
                statusType = "buy"
            )

            // List
            listingListScreenRobot
                .waitUntilListShown()
        }
    }

    private fun setupListingResponse(listingType: List<String>, statusType: String) {

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

    private fun setupProjectResponse() {
        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/project-details/2842",
            filename = "raw/project_detail.json",
            httpMethod = HttpMethod.GET,
            status = HttpURLConnection.HTTP_OK
        )
    }

    private fun setupProjectChildDetailResponse() {
        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/property-details/2019256252",
            filename = "raw/project_child_detail.json",
            httpMethod = HttpMethod.GET,
            status = HttpURLConnection.HTTP_OK
        )
    }

    private fun setupPropertyDetailResponse() {
        webServerDispatcher.addResponse(
            context = context,
            pathPattern = "v1/property-details/2019150933",
            filename = "raw/property_detail.json",
            httpMethod = HttpMethod.GET,
            status = HttpURLConnection.HTTP_OK
        )

    }
}