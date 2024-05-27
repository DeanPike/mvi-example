package au.com.deanpike.detail.data.cache

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.data.datasource.remote.ProjectDetailDataSource
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSource
import au.com.deanpike.network.model.external.detail.ChildListing
import au.com.deanpike.network.model.external.detail.ProjectDetail
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
    private val propertyDataSource: PropertyDetailDataSource = mockk()
    private val projectDataSource: ProjectDetailDataSource = mockk()
    private lateinit var store: Store<ListingKey, ListingDetail>

    @BeforeEach
    fun setupTest() {
        store = getStore()
    }

    @Test
    fun `should fetch property detail from network`() = runTest {
        coEvery {
            propertyDataSource.getPropertyDetails(1234)
        } returns ResponseWrapper.Success(getPropertyNetworkListing())

        // get data from network
        var data = store.get(
            ListingKey(
                type = ListingCacheType.PROPERTY,
                id = 1234
            )
        ) as au.com.deanpike.detail.client.model.detail.PropertyDetail

        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)

        // get data from cache
        data = store.get(
            ListingKey(
                type = ListingCacheType.PROPERTY,
                id = 1234
            )
        ) as au.com.deanpike.detail.client.model.detail.PropertyDetail
        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROPERTY)

        coVerify(exactly = 1) { propertyDataSource.getPropertyDetails(1234) }
    }

    @Test
    fun `should fetch project detail from network`() = runTest {
        coEvery {
            projectDataSource.getProjectDetails(1234)
        } returns ResponseWrapper.Success(getProjectNetworkListing())

        // get data from network
        var data = store.get(
            ListingKey(
                type = ListingCacheType.PROJECT,
                id = 1234
            )
        ) as au.com.deanpike.detail.client.model.detail.ProjectDetail

        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROJECT)
        assertThat(data.childListings[0].id).isEqualTo(5678)

        // get data from cache
        data = store.get(
            ListingKey(
                type = ListingCacheType.PROJECT,
                id = 1234
            )
        ) as au.com.deanpike.detail.client.model.detail.ProjectDetail
        assertThat(data.id).isEqualTo(1234)
        assertThat(data.listingType).isEqualTo(ListingType.PROJECT)
        assertThat(data.childListings[0].id).isEqualTo(5678)

        coVerify(exactly = 1) { projectDataSource.getProjectDetails(1234) }
    }

    private fun getPropertyNetworkListing(): PropertyDetail {
        return PropertyDetail(
            id = 1234,
            listingType = "property"
        )
    }

    private fun getProjectNetworkListing(): ProjectDetail {
        return ProjectDetail(
            id = 1234,
            listingType = "project",
            childListings = listOf(
                ChildListing(
                    id = 5678,
                    lifecycleStatus = "Child"
                )
            )
        )
    }

    private fun getStore(): Store<ListingKey, ListingDetail> {
        return StoreBuilder.from(
            fetcher = ListingFetcherImpl(
                propertyDetailDataSource = propertyDataSource,
                projectDetailDataSource = projectDataSource
            ).createFetcher(),
            sourceOfTruth = ListingTruthImpl().createTruth(),
            converter = ListingConverterImpl().createConverter()
        )
            .build()
    }
}