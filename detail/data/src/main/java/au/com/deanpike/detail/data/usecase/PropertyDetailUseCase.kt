package au.com.deanpike.detail.data.usecase

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.usecase.PropertyDetailUseCase
import au.com.deanpike.detail.data.repository.PropertyDetailRepository
import javax.inject.Inject

internal class PropertyDetailUseCaseImpl @Inject constructor(
    private val repo: PropertyDetailRepository
) : PropertyDetailUseCase {
    override suspend fun getPropertyDetails(id: Int): ResponseWrapper<PropertyDetail> {
        return repo.getDetails(id)
    }
}