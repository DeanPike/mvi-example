package au.com.deanpike.listings.data.converter

import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocation


internal interface SuggestedLocationConverter {
    fun convertSuggestedLocation(suggestedLocation: SuggestedLocation): Location
}

internal class SuggestedLocationConverterImpl : SuggestedLocationConverter {
    override fun convertSuggestedLocation(suggestedLocation: SuggestedLocation): Location {
        return Location(
            nameSlug = suggestedLocation.nameSlug,
            displayName = suggestedLocation.displayName
        )
    }

}