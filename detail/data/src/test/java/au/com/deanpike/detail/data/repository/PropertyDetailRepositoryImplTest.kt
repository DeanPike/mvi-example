package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.data.cache.ListingCache
import au.com.deanpike.detail.data.cache.ListingCacheType
import au.com.deanpike.detail.data.cache.ListingKey
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

class PropertyDetailRepositoryImplTest {
    private val cache: ListingCache = mockk()

    private lateinit var store: Store<ListingKey, ListingDetail>
    private lateinit var repo: PropertyDetailRepository

    @BeforeEach
    fun setupTest() {
        val fetcher = Fetcher.of<ListingKey, ListingDetail> { key ->
            if (key.type == ListingCacheType.PROPERTY && key.id == 1234L) {
                getPropertyDetail()
            } else {
                throw Exception()
            }
        }

        store = StoreBuilder
            .from(
                fetcher = fetcher
            )
            .cachePolicy(
                MemoryPolicy
                    .builder<ListingKey, ListingDetail>()
                    .build()
            )
            .build()

        coEvery { cache.getStore() } returns store
        repo = PropertyDetailRepositoryImpl(
            cache = cache
        )
    }

    @Test
    fun `should convert property detail`() = runTest {
        val response = repo.getDetails(1234)

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data).isInstanceOf(PropertyDetail::class.java)
        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)
    }

    private fun getPropertyDetail(): ListingDetail {
        return PropertyDetail(
            id = 1234,
            listingType = ListingType.PROPERTY,
            address = "Address",
            advertiser = null,
            bathroomCount = 2,
            carSpaceCount = 1,
            bedroomCount = 3,
            dateSold = null,
            description = "Description",
            dwellingType = "House",
            headline = "Headline",
            lifecycleStatus = "Lifecycle",
            media = emptyList(),
            price = "$1,000,000",
            saleType = null,
            schools = emptyList()
        )
    }
}