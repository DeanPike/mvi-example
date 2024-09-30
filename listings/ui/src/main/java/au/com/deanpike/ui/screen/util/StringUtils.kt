package au.com.deanpike.ui.screen.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import au.com.deanpike.listings.ui.R
import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType

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
}