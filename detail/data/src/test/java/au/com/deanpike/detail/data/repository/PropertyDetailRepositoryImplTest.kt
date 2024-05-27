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
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.impl.extensions.get

class PropertyDetailRepositoryImplTest {
    private val cache: ListingCache = mockk()
    private val store: Store<ListingKey, ListingDetail> = mockk()

    private lateinit var repo: PropertyDetailRepository

    @BeforeEach
    fun setupTest() {
        coEvery { cache.getStore() } returns store
        repo = PropertyDetailRepositoryImpl(
            cache = cache
        )
    }

    @Test
    fun `should convert property detail`() = runTest {
        val slot = slot<ListingKey>()
        coEvery {
            store.get(capture(slot))
        } returns getPropertyDetail()

        val response = repo.getDetails(1234)

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data).isInstanceOf(PropertyDetail::class.java)
        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)

        val key = slot.captured
        assertThat(key.type).isEqualTo(ListingCacheType.PROPERTY)
        assertThat(key.id).isEqualTo(1234)
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