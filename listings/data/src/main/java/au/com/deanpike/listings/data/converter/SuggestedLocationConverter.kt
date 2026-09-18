package au.com.deanpike.listings.data.converter

import au.com.deanpike.listings.client.model.suggestedlocation.Location
import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocation
import javax.inject.Inject

internal interface SuggestedLocationConverter {
    fun convertSuggestedLocation(suggestedLocation: SuggestedLocation): Location
}

internal class SuggestedLocationConverterImpl @Inject constructor() : SuggestedLocationConverter {
    override fun convertSuggestedLocation(suggestedLocation: SuggestedLocation): Location {
        return Location(
            displayName = suggestedLocation.displayName,
            name = suggestedLocation.name,
            state = suggestedLocation.state,
            regionName = suggestedLocation.regionName,
            areaName = suggestedLocation.areaName,
            postCode = suggestedLocation.postCode,
            suburbId = suggestedLocation.suburbId,
            nameSlug = suggestedLocation.nameSlug,
            category = suggestedLocation.category,
            group = suggestedLocation.group
        )
    }

}