package au.com.deanpike.ui.screen.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import au.com.deanpike.ui.R
import au.com.deanpike.client.type.ListingType
import au.com.deanpike.client.type.StatusType

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
    fun getPropertyTypeDescription(type: ListingType): String {
        return when (type) {
            ListingType.ALL -> stringResource(id = R.string.all)
            ListingType.HOUSE -> stringResource(id = R.string.house)
            ListingType.TOWNHOUSE -> stringResource(id = R.string.townhouse)
            ListingType.APARTMENT_UNIT_FLAT -> stringResource(id = R.string.apartment_unit_flat)
        }
    }
}