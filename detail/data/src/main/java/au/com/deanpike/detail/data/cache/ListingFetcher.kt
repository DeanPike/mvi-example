package au.com.deanpike.detail.data.cache

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSource
import au.com.deanpike.network.model.external.detail.Detail
import javax.inject.Inject
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.FetcherResult

internal interface ListingFetcher {
    fun createFetcher(): Fetcher<ListingKey, Detail>
}

internal class ListingFetcherImpl @Inject constructor(
    private val propertyDetailDataSource: PropertyDetailDataSource
) : ListingFetcher {
    override fun createFetcher(): Fetcher<ListingKey, Detail> {
        return Fetcher.ofResult { key: ListingKey ->
            if (key.type == ListingCacheType.PROPERTY) {
                when (val data = propertyDetailDataSource.getPropertyDetails(key.id)) {
                    is ResponseWrapper.Success -> {
                        FetcherResult.Data(data.data)
                    }
                    is ResponseWrapper.Error -> {
                        FetcherResult.Error.Exception(data.exception)
                    }
                }

            } else {
                FetcherResult.Error.Exception(Exception())
            }
        }
    }
}