package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.data.converter.PropertyConverter
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSource
import javax.inject.Inject

internal interface PropertyDetailRepository {
    suspend fun getDetails(id: Int): ResponseWrapper<PropertyDetail>
}

internal class PropertyDetailRepositoryImpl @Inject constructor(
    private val dataSource: PropertyDetailDataSource,
    private val propertyConverter: PropertyConverter
) : PropertyDetailRepository {
    override suspend fun getDetails(id: Int): ResponseWrapper<PropertyDetail> {
        when (val response = dataSource.getPropertyDetails(id)) {
            is ResponseWrapper.Success -> {
                val data = response.data

                return ResponseWrapper.Success(propertyConverter.convertDetail(data))
            }
            is ResponseWrapper.Error -> {
                return ResponseWrapper.Error(response.exception)
            }
        }
    }
}