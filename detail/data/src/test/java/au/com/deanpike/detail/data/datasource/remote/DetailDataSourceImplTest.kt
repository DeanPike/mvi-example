package au.com.deanpike.detail.data.datasource.remote

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.network.api.PropertyDetailApi
import au.com.deanpike.network.model.external.detail.Advertiser
import au.com.deanpike.network.model.external.detail.PropertyDetail
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetailDataSourceImplTest {
    private val api: PropertyDetailApi = mockk()
    private lateinit var dataSource: DetailDataSource

    @BeforeEach
    fun setupTest() {
        dataSource = DetailDataSourceImpl(
            api = api
        )
    }

    @Test
    fun `should get details`() = runTest {
        coEvery {
            api.getPropertyDetails(
                contentType = "application/json",
                id = "1468"
            )
        } returns getResponse()

        val response = dataSource.getListingDetails(1468)

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data.id).isEqualTo(1468)
    }

    @Test
    fun `should return an error`() = runTest {
        coEvery {
            api.getPropertyDetails(
                contentType = "application/json",
                id = "1468"
            )
        } throws IOException("Error")

        val response = dataSource.getListingDetails(1468)

        assertThat(response).isInstanceOf(ResponseWrapper.Error::class.java)
        val error = (response as ResponseWrapper.Error).exception
    }

    private fun getResponse(): PropertyDetail {
        return PropertyDetail(
            id = 1468,
            listingType = "Lisrting detail",
            address = "Address",
            headline = "Headline",
            description = "Description",
            lifecycleStatus = "Status",
            media = emptyList(),
            advertiser = Advertiser(
                id = 5678,
                name = "Advertiser",
                address = "Advertiser address",
                preferredColorHex = "Colour",
                agencyListingContacts = arrayListOf(),
            ),
            schools = arrayListOf(),
            dwellingType = "DwellingType",
            price = "Price",
            bedroomCount = 4,
            bathroomCount = 3,
            carspaceCount = 2,
        )
    }
}