package au.com.deanpike.detail.data.cache

import au.com.deanpike.detail.client.model.detail.ListingDetail
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

internal interface ListingCache {
    fun getStore(): Store<ListingKey, ListingDetail>
}

internal class ListingCacheImpl @Inject constructor(
    truth: ListingTruth,
    fetcher: ListingFetcher,
    converter: ListingConverter
) : ListingCache {

    private var store: Store<ListingKey, ListingDetail> = StoreBuilder.from(
        fetcher = fetcher.createFetcher(),
        sourceOfTruth = truth.createTruth(),
        converter = converter.createConverter()
    )
        .cachePolicy(
            MemoryPolicy.builder<Any, Any>()
                .setMaxSize(20)
                .setExpireAfterAccess(10.minutes)
                .build()
        )
        .build()

    override fun getStore(): Store<ListingKey, ListingDetail> = store
}