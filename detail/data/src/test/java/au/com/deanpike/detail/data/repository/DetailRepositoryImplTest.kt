package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.data.converter.DetailConverterFactory
import au.com.deanpike.detail.data.converter.PropertyConverter
import au.com.deanpike.detail.data.datasource.remote.DetailDataSource
import au.com.deanpike.network.model.external.detail.PropertyDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetailRepositoryImplTest {
    private val dataSource: DetailDataSource = mockk()
    private val converterFactory: DetailConverterFactory = mockk()

    private lateinit var repo: DetailRepository

    @BeforeEach
    fun setupTest() {
        repo = DetailRepositoryImpl(
            dataSource = dataSource,
            converterFactory = converterFactory
        )
    }

    @Test
    fun `should convert property detail`() = runTest {
        coEvery {
            dataSource.getListingDetails(1234)
        } returns ResponseWrapper.Success(getNetworkListing())

        coEvery { converterFactory.getConverter(ListingType.PROPERTY) } returns PropertyConverter()

        val response = repo.getDetails(1234)

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data).isInstanceOf(PropertyDetail::class.java)
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