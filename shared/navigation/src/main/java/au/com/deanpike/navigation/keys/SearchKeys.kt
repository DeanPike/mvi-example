package au.com.deanpike.navigation.keys

import androidx.navigation3.runtime.NavKey
import au.com.deanpike.listings.client.model.suggestedlocation.Location
import kotlinx.serialization.Serializable

@Serializable
data class SearchScreenKey(
    val location: Location? = null,
    val status: String = "BUY",
    val dwellingTypes: List<String> = listOf("ALL")
) : NavKey
