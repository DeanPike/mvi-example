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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.AgencyBannerTestTags.AGENCY_BANNER_LAYOUT
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage

@Composable
fun AgencyBannerComponent(
    modifier: Modifier = Modifier,
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
            )
            .testTag(AGENCY_BANNER_LAYOUT),
        horizontalArrangement = Arrangement.End
    ) {
        if (logo != null) {
            AsyncImage(
                modifier = Modifier
                    .height(40.dp)
                    .testTag(AgencyBannerTestTags.AGENCY_BANNER_IMAGE),
                placeholder = painterResource(id = R.drawable.gallery_placeholder),
                model = logo,
                contentDescription = stringResource(id = R.string.property_image_description),
                error = painterResource(id = R.drawable.gallery_placeholder),
                fallback = painterResource(id = R.drawable.gallery_placeholder)
            )
        } else {
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(
                        color = if ((agencyColour?.length ?: 0) == 7) {
                            Color(android.graphics.Color.parseColor(agencyColour))
                        } else {
                            MaterialTheme.colorScheme.background
                        }
                    )
                    .testTag(AgencyBannerTestTags.AGENCY_BANNER_IMAGE)
            ) {
            }
        }
    }
}

object AgencyBannerTestTags {
    private const val PREFIX = "AGENCY_BANNER_"
    const val AGENCY_BANNER_LAYOUT = "${PREFIX}LAYOUT"
    const val AGENCY_BANNER_IMAGE = "${PREFIX}IMAGE"
}

@Preview
@Composable
fun AgencyBannerPreview() {
    MviExampleTheme {
        AgencyBannerComponent(
            agencyColour = "#ffffff",
            logo = "https://images.domain.com.au/img/Agencys/17114/logo_17114.png?buster=2024-04-01"
        )
    }
}