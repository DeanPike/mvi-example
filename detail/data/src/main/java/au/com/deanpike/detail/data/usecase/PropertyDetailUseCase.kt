package au.com.deanpike.detail.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.ListingDetail
import au.com.deanpike.detail.client.usecase.PropertyDetailUseCase
import au.com.deanpike.detail.data.repository.PropertyDetailRepository
import javax.inject.Inject

internal class PropertyDetailUseCaseImpl @Inject constructor(
    private val repo: PropertyDetailRepository
) : PropertyDetailUseCase {
    override suspend fun getDetails(id: Int): ResponseWrapper<ListingDetail?> {
        return repo.getDetails(id)
    }
}