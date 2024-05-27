package au.com.deanpike.detail.data.cache

import au.com.deanpike.detail.client.model.detail.ListingDetail
import javax.inject.Inject
import org.mobilenativefoundation.store.store5.SourceOfTruth

internal interface ListingTruth {
    fun createTruth(): SourceOfTruth<ListingKey, ListingDetail, ListingDetail>
}

internal class ListingTruthImpl @Inject constructor(

) : ListingTruth {

    private val listings = mutableMapOf<ListingKey, ListingDetail>()

    override fun createTruth(): SourceOfTruth<ListingKey, ListingDetail, ListingDetail> {
        return SourceOfTruth.of(
            nonFlowReader = { key: ListingKey ->
                listings[key]
            },
            writer = { key: ListingKey, detail: ListingDetail ->
                listings[key] = detail
            },
            delete = { key: ListingKey ->
                listings.remove(key)
            },
            deleteAll = {
                listings.clear()
            }
        )
    }
}