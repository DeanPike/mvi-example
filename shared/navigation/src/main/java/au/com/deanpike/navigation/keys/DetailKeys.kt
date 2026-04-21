package au.com.deanpike.navigation.keys

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class PropertyDetailScreenKey(
    val propertyId: Long,
    val address: String
) : NavKey

@Serializable
data class ProjectDetailScreenKey(
    val projectId: Long,
    val address: String
) : NavKey


@Serializable
data class FullSizeImageComponentKey(
    val imageUrl: String
) : NavKey

@Serializable
data object DefaultDetailScreenKey : NavKey