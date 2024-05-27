package au.com.deanpike.detail.data.cache

import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.data.converter.ProjectConverter
import au.com.deanpike.detail.data.converter.PropertyConverter
import au.com.deanpike.network.model.external.detail.Detail
import au.com.deanpike.network.model.external.detail.ProjectDetail
import au.com.deanpike.network.model.external.detail.PropertyDetail
import javax.inject.Inject
import org.mobilenativefoundation.store.store5.Converter

internal interface ListingConverter {
    fun createConverter(): Converter<Detail, ListingDetail, ListingDetail>
}

internal class ListingConverterImpl @Inject constructor() : ListingConverter {
    override fun createConverter(): Converter<Detail, ListingDetail, ListingDetail> {
        return Converter.Builder<Detail, ListingDetail, ListingDetail>()
            .fromNetworkToLocal { network: Detail ->
                if (network is PropertyDetail) {
                    PropertyConverter.convertDetail(network)
                } else {
                    ProjectConverter.convertDetail(network as ProjectDetail)
                }
            }
            .fromOutputToLocal { output: ListingDetail ->
                output
            }
            .build()
    }
}