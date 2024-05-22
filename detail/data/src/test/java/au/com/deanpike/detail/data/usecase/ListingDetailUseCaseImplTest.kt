package au.com.deanpike.detail.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.usecase.PropertyDetailUseCase
import au.com.deanpike.detail.data.repository.PropertyDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListingDetailUseCaseImplTest {
    private val repo: PropertyDetailRepository = mockk()
    private lateinit var useCase: PropertyDetailUseCase

    @BeforeEach
    fun setupTest() {
        useCase = PropertyDetailUseCaseImpl(
            repo = repo
        )
    }

    @Test
    fun `should get listing details`() = runTest {
        coEvery { repo.getDetails(1234) } returns
            ResponseWrapper.Success(
                PropertyDetail(
                    id = 1234,
                    listingType = ListingType.PROPERTY,
                    address = "Address",
                    headline = "Headline",
                    description = "Description",
                    lifecycleStatus = "Status",
                    media = emptyList(),
                    advertiser = Advertiser(
                        id = 5678,
                        name = "Advertiser name",
                        address = "Advertiser address",
                        logoUrl = "http://advertiser.logo.url",
                        agencyBannerImageUrl = "http://agency.banner.url",
                        preferredColorHex = "Colour",
                        agencyListingContacts = emptyList(),
                    ),
                    schools = emptyList(),
                    dwellingType = "House",
                    price = "$1,000,000",
                    bedroomCount = 4,
                    bathroomCount = 3,
                    carSpaceCount = 2,
                    dateSold = "21/05/2024",
                    saleType = "Auction"
                )
            )
        val details = useCase.getDetails(1234)

        assertThat(details).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (details as ResponseWrapper.Success).data
        assertThat(data).isInstanceOf(PropertyDetail::class.java)
        assertThat(data!!.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)
    }
}