package au.com.deanpike.network.model.external.suggestedlocation

import com.google.gson.annotations.SerializedName

data class SuggestedLocations(
    @SerializedName("Locations")
    val suggestedLocations: List<SuggestedLocation>
)