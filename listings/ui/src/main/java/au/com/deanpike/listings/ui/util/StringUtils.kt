package au.com.deanpike.listings.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.R

object StringUtils {
    @Composable
    fun getStatusDescription(type: StatusType): String {
        return when (type) {
            StatusType.BUY -> stringResource(id = R.string.buy)
            StatusType.RENT -> stringResource(id = R.string.rent)
            StatusType.SOLD -> stringResource(id = R.string.sold)
        }
    }

    @Composable
    fun getPropertyTypeDescription(type: DwellingType): String {
        return when (type) {
            DwellingType.ALL -> stringResource(id = R.string.all)
            DwellingType.HOUSE -> stringResource(id = R.string.house)
            DwellingType.TOWNHOUSE -> stringResource(id = R.string.townhouse)
            DwellingType.APARTMENT_UNIT_FLAT -> stringResource(id = R.string.apartment_unit_flat)
        }
    }

    fun createDetailsText(
        dwellingType: String?,
        numberOfBedrooms: Int?,
        numberOfBathrooms: Int?,
        numberOfCarSpaces: Int?
    ): String {
        val details = mutableListOf<String>()
        dwellingType?.let { details.add(it) }
        numberOfBedrooms?.let { details.add("$it Bed") }
        numberOfBathrooms?.let { details.add("$it Bath") }
        numberOfCarSpaces?.let { details.add("$it Car") }
        return details.joinToString(" • ")
    }
}