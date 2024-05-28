package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.client.model.detail.ProjectDetail
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

class ProjectDetailRepositoryImplTest {
    private val cache: ListingCache = mockk()

    private lateinit var store: Store<ListingKey, ListingDetail>
    private lateinit var repo: ProjectDetailRepository

    @BeforeEach
    fun setupTest() {
        val fetcher = Fetcher.of<ListingKey, ListingDetail> { key ->
            if (key.type == ListingCacheType.PROJECT && key.id == 1234) {
                getProjectDetail()
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
        repo = ProjectDetailRepositoryImpl(
            cache = cache
        )
    }

    @Test
    fun `should convert project detail`() = runTest {
        val response = repo.getDetails(1234)

        assertThat(response).isInstanceOf(ResponseWrapper.Success::class.java)
        val data = (response as ResponseWrapper.Success).data
        assertThat(data).isInstanceOf(ProjectDetail::class.java)
        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROJECT)
        assertThat(data.childListings[0].id).isEqualTo(456)
    }

    private fun getProjectDetail(): ListingDetail {
        return ProjectDetail(
            id = 1234,
            listingType = ListingType.PROJECT,
            childListings = listOf(
                ProjectChild(
                    id = 456
                )
            )
        )
    }
}