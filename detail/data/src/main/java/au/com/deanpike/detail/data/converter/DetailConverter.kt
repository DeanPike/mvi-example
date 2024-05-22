package au.com.deanpike.detail.data.converter

import au.com.deanpike.network.model.external.detail.PropertyDetail

interface DetailConverter {
    fun convertDetail(detail: PropertyDetail): au.com.deanpike.detail.client.model.detail.ListingDetail
}