package au.com.deanpike.detail.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.client.usecase.ListingDetailUseCase
import au.com.deanpike.detail.data.repository.PropertyDetailRepository
import javax.inject.Inject

internal class ListingDetailUseCaseImpl @Inject constructor(
    private val repo: PropertyDetailRepository
) : ListingDetailUseCase {
    override suspend fun getDetails(id: Int): ResponseWrapper<ListingDetail?> {
        return repo.getDetails(id)
    }
}