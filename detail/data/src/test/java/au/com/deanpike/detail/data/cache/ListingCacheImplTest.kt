package au.com.deanpike.detail.data.cache

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSource
import au.com.deanpike.network.model.external.detail.PropertyDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.impl.extensions.get

class ListingCacheImplTest {
    private val dataSource: PropertyDetailDataSource = mockk()
    private lateinit var store: Store<ListingKey, ListingDetail>

    @BeforeEach
    fun setupTest() {
        store = getStore()
    }


    @Test
    fun `should fetch detail from network`() = runTest {
        coEvery {
            dataSource.getPropertyDetails(1234)
        } returns ResponseWrapper.Success(getNetworkListing())

        // get data from network
        var data = store.get(
            ListingKey(
                type = ListingCacheType.PROPERTY,
                id = 1234
            )
        )

        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)

        // get data from cache
        data = store.get(
            ListingKey(
                type = ListingCacheType.PROPERTY,
                id = 1234
            )
        )
        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)

        coVerify(exactly = 1) { dataSource.getPropertyDetails(1234) }
    }

    private fun getNetworkListing(): PropertyDetail {
        return PropertyDetail(
            id = 1234,
            listingType = "property"
        )
    }

    private fun getStore(): Store<ListingKey, ListingDetail> {
        return StoreBuilder.from(
            fetcher = ListingFetcherImpl(dataSource).createFetcher(),
            sourceOfTruth = ListingTruthImpl().createTruth(),
            converter = ListingConverterImpl().createConverter()
        )
            .build()
    }
}