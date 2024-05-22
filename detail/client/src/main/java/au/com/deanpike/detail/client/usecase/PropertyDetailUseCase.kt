package au.com.deanpike.detail.client.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.ListingDetail

interface PropertyDetailUseCase {
    suspend fun getDetails(id: Int): ResponseWrapper<ListingDetail?>
}