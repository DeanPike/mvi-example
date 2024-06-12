package au.com.deanpike.detail.ui.property

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.usecase.PropertyDetailUseCase
import au.com.deanpike.testshared.extension.TestDispatcherExtension
import au.com.deanpike.uishared.base.ScreenStateType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestDispatcherExtension::class)
class PropertyDetailViewModelTest {
    private val useCase: PropertyDetailUseCase = mockk()

    private lateinit var viewModel: PropertyDetailViewModel

    @BeforeEach
    fun setupTest(dispatcherProvider: DispatcherProvider) {
        viewModel = PropertyDetailViewModel(
            dispatcher = dispatcherProvider,
            useCase = useCase
        )
    }

    @Test
    fun `should get property details`() = runTest {
        coEvery {
            useCase.getPropertyDetails(12)
        } returns ResponseWrapper.Success(
            getProperty()
        )

        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = 12))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.SUCCESS)
            assertThat(propertyId).isEqualTo(12)
            assertThat(propertyDetail).isNotNull
            assertThat(propertyDetail!!.id).isEqualTo(12)
            assertThat(propertyDetail!!.listingType).isEqualTo(ListingType.PROPERTY)
            assertThat(propertyDetail!!.address).isEqualTo("Property address")
        }
    }

    @Test
    fun `should handle an error when getting property details`() = runTest {
        coEvery {
            useCase.getPropertyDetails(12)
        } returns ResponseWrapper.Error(
            Exception("Error")
        )

        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = 12))
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.ERROR)
            assertThat(propertyId).isEqualTo(12)
            assertThat(propertyDetail).isNull()
        }
    }

    @Test
    fun `should handle retry on error`() = runTest {
        coEvery {
            useCase.getPropertyDetails(12)
        } returns ResponseWrapper.Error(
            Exception("Error")
        )
        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = 12))
        advanceUntilIdle()

        // Test
        viewModel.setEvent(PropertyDetailScreenEvent.OnRetryClicked)
        advanceUntilIdle()

        with(viewModel.uiState) {
            assertThat(screenState).isEqualTo(ScreenStateType.ERROR)
        }

        coVerify(exactly = 2) { useCase.getPropertyDetails(12) }
    }

    private fun getProperty(): PropertyDetail {
        return PropertyDetail(
            id = 12,
            listingType = ListingType.PROPERTY,
            address = "Property address"
        )
    }
}