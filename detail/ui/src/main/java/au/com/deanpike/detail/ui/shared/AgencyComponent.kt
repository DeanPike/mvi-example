package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_ADDRESS
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_LAYOUT
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_NAME
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENT_LABEL
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun AgencyComponent(
    advertiser: Advertiser
) {
    Column(
        modifier = Modifier
            .padding(bottom = DIM_16)
            .testTag(AGENCY_LAYOUT)
    ) {
        Text(
            modifier = Modifier
                .padding(start = DIM_16, top = DIM_8, bottom = DIM_8)
                .testTag(AGENT_LABEL),
            text = stringResource(id = R.string.agent),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )

        advertiser.name?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = DIM_16, end = DIM_16, top = DIM_8)
                    .testTag(AGENCY_NAME),
                text = it,
                style = MaterialTheme.typography.titleMedium
            )
        }
        advertiser.address?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = DIM_16, end = DIM_16)
                    .testTag(AGENCY_ADDRESS),
                text = it,
            )
        }
        AgentComponent(agents = advertiser.agencyListingContacts)
    }
}

object AgencyComponentTestTags {
    private const val PREFIX = "AGENCY_"
    const val AGENCY_LAYOUT = "${PREFIX}LAYOUT"
    const val AGENT_LABEL = "${PREFIX}_LABEL"
    const val AGENCY_NAME = "${PREFIX}NAME"
    const val AGENCY_ADDRESS = "${PREFIX}ADDRESS"
}

@Preview(showBackground = true)
@Composable
fun AgencyComponentPreview() {
    val advertiser = Advertiser(
        id = 2373,
        name = "Ray White Wetherill Park",
        address = "Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164",
        logoUrl = """https://images.domain.com.au/img/Agencys/2373/logo_2373.jpg?buster=2024-06-03""",
        agencyBannerImageUrl = """https://images.domain.com.au/img/Agencys/2373/banner_2373.jpg?buster=2024-06-03""",
        preferredColorHex = "#FEE536",
        agencyListingContacts = listOf(
            Agent(
                id = "1697102",
                address = """Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164""",
                name = "Riccardo Romolo",
                imageUrl = """https://images.domain.com.au/img/2373/contact_1697102.jpeg?buster=2024-06-03""",
                emailAddress = "riccardo.romolo@raywhite.com",
                phoneNumbers = listOf(
                    PhoneNumber(
                        type = PhoneNumberType.MOBILE,
                        label = "Mobile",
                        number = "0452 184 976"
                    ),
                    PhoneNumber(
                        type = PhoneNumberType.GENERAL,
                        label = "General",
                        number = "02 9609 7099"
                    ),
                    PhoneNumber(
                        type = PhoneNumberType.FAX,
                        label = "Fax",
                        number = "02 9609 2370"
                    )
                )
            ),
            Agent(
                id = "1350251",
                address = """Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164""",
                name = "Marcus Biasetto with added text to make the name wrap over two lines",
                imageUrl = """https://images.domain.com.au/img/2373/contact_1350251.png?buster=2024-06-05""",
                emailAddress = "marcus.biasetto@raywhite.com",
                phoneNumbers = listOf(
                    PhoneNumber(
                        type = PhoneNumberType.MOBILE,
                        label = "Mobile",
                        number = "0414 246 947"
                    ),
                    PhoneNumber(
                        type = PhoneNumberType.GENERAL,
                        label = "General",
                        number = "02 9609 7099"
                    ),
                    PhoneNumber(
                        type = PhoneNumberType.FAX,
                        label = "Fax",
                        number = "02 9609 2370"
                    )
                )
            )
        ),
    )

    MviExampleTheme {
        AgencyComponent(
            advertiser = advertiser
        )
    }
}