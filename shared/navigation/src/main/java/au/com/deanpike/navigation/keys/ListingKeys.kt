package au.com.deanpike.navigation.keys

import androidx.navigation3.runtime.NavKey
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import kotlinx.serialization.Serializable

@Serializable
data class ListingScreenKey(
    val location: Location? = null,
    val status: String,
    val dwellingTypes: List<String>
) : NavKey
