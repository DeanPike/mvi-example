package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.data.converter.PropertyConverter
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSource
import au.com.deanpike.network.model.external.detail.PropertyDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PropertyDetailRepositoryImplTest {
    private val dataSource: PropertyDetailDataSource = mockk()
    private val propertyConverter = PropertyConverter()

    private lateinit var repo: PropertyDetailRepository

    @BeforeEach
    fun setupTest() {
        repo = PropertyDetailRepositoryImpl(
            dataSource = dataSource,
            propertyConverter = propertyConverter
        )
    }

    @Test
    fun `should convert property detail`() = runTest {
        coEvery {
            dataSource.getListingDetails(1234)
        } returns ResponseWrapper.Success(getNetworkListing())

        val response = repo.getDetails(1234)

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data).isInstanceOf(au.com.deanpike.detail.client.model.detail.PropertyDetail::class.java)
        assertThat(data!!.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)
    }

    private fun getNetworkListing(): PropertyDetail {
        return PropertyDetail(
            id = 1234,
            listingType = "property"
        )
    }
}