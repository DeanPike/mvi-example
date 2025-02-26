package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.data.cache.ListingCache
import au.com.deanpike.detail.data.cache.ListingCacheType
import au.com.deanpike.detail.data.cache.ListingKey
import javax.inject.Inject
import org.mobilenativefoundation.store.store5.impl.extensions.get

internal interface PropertyDetailRepository {
    suspend fun getDetails(id: Long): ResponseWrapper<PropertyDetail>
}

internal class PropertyDetailRepositoryImpl @Inject constructor(
    private val cache: ListingCache
) : PropertyDetailRepository {
    override suspend fun getDetails(id: Long): ResponseWrapper<PropertyDetail> {
        return try {
            val detail = cache.getStore().get(
                ListingKey(
                    type = ListingCacheType.PROPERTY,
                    id = id
                )
            )
            ResponseWrapper.Success(detail as PropertyDetail)
        } catch (e: Exception) {
            ResponseWrapper.Error(e)
        }
    }
}