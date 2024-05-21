package au.com.deanpike.detail.data.converter

import au.com.deanpike.network.model.external.detail.ListingDetail

interface DetailConverter {
    fun convertDetail(detail: ListingDetail): au.com.deanpike.detail.client.model.detail.ListingDetail
}