package au.com.deanpike.navigation.keys

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class ListingScreenKey(
    val locationDisplayName: String? = null,
    val locationName: String? = null,
    val locationState: String? = null,
    val locationRegionName: String? = null,
    val locationAreaName: String? = null,
    val locationPostCode: String? = null,
    val locationSuburbId: String? = null,
    val locationNameSlug: String? = null,
    val locationCategory: String? = null,
    val locationGroup: String? = null,
    val status: String,
    val dwellingTypes: List<String>
) : NavKey
