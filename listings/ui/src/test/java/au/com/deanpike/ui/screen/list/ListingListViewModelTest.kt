package au.com.deanpike.ui.screen.list

import au.com.deanpike.listings.client.model.listing.response.ListingDetails
import au.com.deanpike.listings.client.model.listing.response.ListingType
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.client.model.listing.search.ListingSearch
import au.com.deanpike.listings.client.type.DwellingType.HOUSE
import au.com.deanpike.listings.client.type.DwellingType.TOWNHOUSE
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.client.usecase.ListingUseCase
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.testshared.extension.TestDispatcherExtension
import au.com.deanpike.uishared.base.ScreenStateType
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestDispatcherExtension::class)
class ListingListViewModelTest {

    private val useCase: ListingUseCase = mockk()

    private lateinit var viewModel: ListingListViewModel

    @BeforeEach
    fun setupTest(dispatcherProvider: DispatcherProvider) {
        viewModel = ListingListViewModel(
            dispatcher = dispatcherProvider,
            listingUseCase = useCase
        )
    }

    @Test
    fun `should initialise view model`() = runTest {
        coEvery {
            useCase.getListings(
                ListingSearch(
                    searchMode = StatusType.BUY,
                    dwellingTypes = emptyList()
                )
            )
        } returns ResponseWrapper.Success(listOf(getProject(), getProperty()))

        viewModel.setEvent(ListingListScreenEvent.Initialise)
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.SUCCESS)
            assertThat(listings.size).isEqualTo(2)
        }

        // Project
        assertThat(viewModel.uiState.listings[0]).isInstanceOf(Project::class.java)
        with(viewModel.uiState.listings[0] as Project) {
            assertThat(id).isEqualTo(2)
            assertThat(listingType).isEqualTo(au.com.deanpike.listings.client.model.listing.response.ListingType.PROJECT)
            assertThat(address).isEqualTo("Project address")
            assertThat(bannerImage).isEqualTo("http://project.banner.image")
            assertThat(logoImage).isEqualTo("http://project.lgog.image")
            assertThat(projectName).isEqualTo("Project Name")
            assertThat(projectColour).isEqualTo("Red")

            assertThat(properties.size).isEqualTo(2)

            assertThat(properties[0].id).isEqualTo(21)
            assertThat(properties[0].listingType).isEqualTo(au.com.deanpike.listings.client.model.listing.response.ListingType.PROPERTY)
            assertThat(properties[0].lifecycleStatus).isEqualTo("New")
            assertThat(properties[0].listingDetails.price).isEqualTo("850000")
            assertThat(properties[0].listingDetails.numberOfBedrooms).isEqualTo(2)
            assertThat(properties[0].listingDetails.numberOfBathrooms).isEqualTo(1)
            assertThat(properties[0].listingDetails.numberOfCarSpaces).isEqualTo(0)

            assertThat(properties[1].id).isEqualTo(22)
            assertThat(properties[1].listingType).isEqualTo(au.com.deanpike.listings.client.model.listing.response.ListingType.PROPERTY)
            assertThat(properties[1].lifecycleStatus).isEqualTo("Sold")
            assertThat(properties[1].listingDetails.price).isEqualTo("1200000")
            assertThat(properties[1].listingDetails.numberOfBedrooms).isEqualTo(3)
            assertThat(properties[1].listingDetails.numberOfBathrooms).isEqualTo(2)
            assertThat(properties[1].listingDetails.numberOfCarSpaces).isEqualTo(1)
        }

        // Property
        assertThat(viewModel.uiState.listings[1]).isInstanceOf(Property::class.java)
        with(viewModel.uiState.listings[1] as Property) {
            assertThat(id).isEqualTo(1)
            assertThat(listingType).isEqualTo(au.com.deanpike.listings.client.model.listing.response.ListingType.PROPERTY)
            assertThat(address).isEqualTo("Property address")
            assertThat(listingImage).isEqualTo("http://listing.image")
            assertThat(agencyImage).isEqualTo("http://agency.image")
            assertThat(dwellingType).isEqualTo("House")
            assertThat(headLine).isEqualTo("Property headline")
            assertThat(lifecycleStatus).isEqualTo("New")
            assertThat(detail.price).isEqualTo("$1000000")
            assertThat(detail.numberOfBedrooms).isEqualTo(4)
            assertThat(detail.numberOfBathrooms).isEqualTo(3)
            assertThat(detail.numberOfCarSpaces).isEqualTo(2)
            assertThat(agencyColour).isEqualTo("White")
        }
    }

    @Test
    fun `should handle error`() = runTest {
        coEvery {
            useCase.getListings(
                ListingSearch(
                    searchMode = StatusType.BUY,
                    dwellingTypes = emptyList()
                )
            )
        } returns ResponseWrapper.Error(IOException("No Internet"))

        viewModel.setEvent(ListingListScreenEvent.Initialise)
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.ERROR)
            assertThat(0).isEqualTo(listings.size)
        }
    }

    @Test
    fun `should handle status change`() = runTest {
        coEvery {
            useCase.getListings(
                ListingSearch(
                    searchMode = StatusType.RENT,
                    dwellingTypes = emptyList()
                )
            )
        } returns ResponseWrapper.Success(listOf(getProject(), getProperty()))

        viewModel.setEvent(ListingListScreenEvent.OnStatusSelected(StatusType.RENT))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.SUCCESS)
            assertThat(listings.size).isEqualTo(2)
            assertThat(selectedStatus).isEqualTo(StatusType.RENT)
        }
    }

    @Test
    fun `handle listing type clicked`() = runTest {
        viewModel.setEvent(ListingListScreenEvent.OnListingTypeClicked)
        advanceUntilIdle()

        assertThat(viewModel.uiState.showListingTypeScreen).isTrue()

    }

    @Test
    fun `handle bottom sheet dismissed`() = runTest {
        viewModel.setEvent(ListingListScreenEvent.OnBottomSheetDismissed)
        advanceUntilIdle()

        assertThat(viewModel.uiState.showListingTypeScreen).isFalse()
    }

    @Test
    fun `handle listing types applied`() = runTest {
        coEvery {
            useCase.getListings(
                ListingSearch(
                    searchMode = StatusType.BUY,
                    dwellingTypes = listOf(HOUSE, TOWNHOUSE)
                )
            )
        } returns ResponseWrapper.Success(emptyList())

        viewModel.setEvent(
            ListingListScreenEvent.OnListingTypesApplied(
                listOf(
                    HOUSE,
                    TOWNHOUSE
                )
            )
        )
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(showListingTypeScreen).isFalse()
            assertThat(selectedListingTypes.size).isEqualTo(2)
            assertThat(selectedListingTypes[0]).isEqualTo(HOUSE)
            assertThat(selectedListingTypes[1]).isEqualTo(TOWNHOUSE)
            assertThat(screenState).isEqualTo(ScreenStateType.SUCCESS)
        }
    }

    private fun getProperty(): Property {
        return Property(
            id = 1,
            listingType = ListingType.PROPERTY,
            address = "Property address",
            listingImage = "http://listing.image",
            agencyImage = "http://agency.image",
            dwellingType = "House",
            headLine = "Property headline",
            lifecycleStatus = "New",
            agencyColour = "White",
            detail = ListingDetails(
                price = "$1000000",
                numberOfBedrooms = 4,
                numberOfBathrooms = 3,
                numberOfCarSpaces = 2
            )
        )
    }

    private fun getProject(): Project {
        val childOne = ProjectChild(
            id = 21,
            listingType = ListingType.PROPERTY,
            lifecycleStatus = "New",
            listingDetails = ListingDetails(
                price = "850000",
                numberOfBedrooms = 2,
                numberOfBathrooms = 1,
                numberOfCarSpaces = 0
            )
        )

        val childTwo = ProjectChild(
            id = 22,
            listingType = ListingType.PROPERTY,
            lifecycleStatus = "Sold",
            listingDetails = ListingDetails(
                price = "1200000",
                numberOfBedrooms = 3,
                numberOfBathrooms = 2,
                numberOfCarSpaces = 1
            )
        )

        return Project(
            id = 2,
            listingType = ListingType.PROJECT,
            address = "Project address",
            listingImage = "http://project.listing.image",
            bannerImage = "http://project.banner.image",
            logoImage = "http://project.lgog.image",
            projectName = "Project Name",
            properties = listOf(childOne, childTwo),
            projectColour = "Red"
        )
    }
}