package au.com.deanpike.detail.data.repository

import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.detail.data.cache.ListingCache
import au.com.deanpike.detail.data.cache.ListingCacheType
import au.com.deanpike.detail.data.cache.ListingKey
import javax.inject.Inject
import org.mobilenativefoundation.store.store5.impl.extensions.get

internal interface ProjectDetailRepository {
    suspend fun getDetails(id: Int): ResponseWrapper<ProjectDetail>
}

internal class ProjectDetailRepositoryImpl @Inject constructor(
    private val cache: ListingCache
) : ProjectDetailRepository {
    override suspend fun getDetails(id: Int): ResponseWrapper<ProjectDetail> {
        return try {
            val detail = cache.getStore().get(
                ListingKey(
                    type = ListingCacheType.PROJECT,
                    id = id
                )
            )
            ResponseWrapper.Success(detail as ProjectDetail)
        } catch (e: Exception) {
            ResponseWrapper.Error(e)
        }
    }
}