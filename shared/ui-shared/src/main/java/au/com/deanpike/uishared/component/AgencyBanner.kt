package au.com.deanpike.uishared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import au.com.deanpike.uishared.R
import coil.compose.AsyncImage

@Composable
fun AgencyBanner(
    modifier: Modifier = Modifier,
    position: Int = 0,
    agencyColour: String?,
    logo: String?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = if ((agencyColour?.length ?: 0) == 7) {
                    Color(android.graphics.Color.parseColor(agencyColour))
                } else {
                    MaterialTheme.colorScheme.background
                }
            ),
        horizontalArrangement = Arrangement.End
    ) {
        AsyncImage(
            modifier = Modifier
                .height(40.dp)
                .testTag("${AgencyBannerTestTags.AGENCY_BANNER_IMAGE}_$position"),
            placeholder = painterResource(id = R.drawable.gallery_placeholder),
            model = logo,
            contentDescription = stringResource(id = R.string.property_image_description)
        )
    }
}

object AgencyBannerTestTags {
    private const val PREFIX = "AGENCY_BANNER_"
    const val AGENCY_BANNER_IMAGE = "${PREFIX}IMAGE"
}