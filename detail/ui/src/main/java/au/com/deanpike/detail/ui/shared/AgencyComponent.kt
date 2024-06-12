package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import au.com.deanpike.detail.client.model.detail.Advertiser
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_ADDRESS
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_LAYOUT
import au.com.deanpike.detail.ui.shared.AgencyComponentTestTags.AGENCY_NAME
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_CARD
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_EMAIL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_FAX
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_GENERAL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_IMAGE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_MOBILE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_NAME
import au.com.deanpike.uishared.component.AgencyBanner
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage

@Composable
fun AgencyComponent(
    advertiser: Advertiser
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = DIM_16)
            .testTag(AGENCY_LAYOUT)
    ) {
        AgencyBanner(
            agencyColour = advertiser.preferredColorHex,
            logo = advertiser.logoUrl
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
        advertiser.agencyListingContacts.forEachIndexed { index, agent ->
            AgentComponent(
                agent = agent,
                position = index
            )
        }
    }
}

@Composable
fun AgentComponent(
    agent: Agent,
    position: Int
) {
    Card(
        modifier = Modifier
            .padding(top = DIM_8, start = DIM_16, end = DIM_16)
            .testTag("${AGENT_CARD}_$position"),
        colors = CardColors(
            containerColor = Color.Gray.copy(alpha = 0.05F),
            contentColor = CardDefaults.cardColors().contentColor,
            disabledContentColor = CardDefaults.cardColors().disabledContentColor,
            disabledContainerColor = CardDefaults.cardColors().disabledContainerColor
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(DIM_4)
        ) {
            val (nameRef, imageRef, contactDetailsRef) = createRefs()

            agent.name?.let {
                Text(
                    modifier = Modifier
                        .constrainAs(nameRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(imageRef.start)
                            width = Dimension.fillToConstraints
                        }
                        .padding(start = DIM_8)
                        .testTag("${AGENT_NAME}_$position"),
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            agent.imageUrl?.let {
                AsyncImage(
                    modifier = Modifier
                        .constrainAs(imageRef) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(top = DIM_4, end = DIM_4)
                        .size(60.dp)
                        .clip(CircleShape)
                        .testTag("${AGENT_IMAGE}_$position"),
                    model = it,
                    contentDescription = stringResource(id = R.string.agent_photo),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = au.com.deanpike.uishared.R.drawable.gallery_placeholder)
                )
            }

            Column(modifier = Modifier
                .constrainAs(contactDetailsRef) {
                    start.linkTo(parent.start)
                    top.linkTo(nameRef.bottom)
                    end.linkTo(imageRef.start)
                    width = Dimension.fillToConstraints
                }
                .padding(top = DIM_8)
            ) {
                agent.phoneNumbers.firstOrNull {
                    it.type == PhoneNumberType.MOBILE
                }?.let {
                    ContactComponent(
                        label = stringResource(id = R.string.mobile),
                        number = it.number ?: "",
                        testTag = "${AGENT_MOBILE}_$position"
                    )
                }
                agent.phoneNumbers.firstOrNull() {
                    it.type == PhoneNumberType.GENERAL
                }?.let {
                    ContactComponent(
                        label = stringResource(id = R.string.general),
                        number = it.number ?: "",
                        testTag = "${AGENT_GENERAL}_$position"
                    )
                }
                agent.phoneNumbers.firstOrNull() {
                    it.type == PhoneNumberType.FAX
                }?.let {
                    ContactComponent(
                        label = stringResource(id = R.string.fax),
                        number = it.number ?: "",
                        testTag = "${AGENT_FAX}_$position"
                    )
                }

                agent.emailAddress?.let {
                    ContactComponent(
                        label = stringResource(id = R.string.email),
                        number = it,
                        testTag = "${AGENT_EMAIL}_$position"
                    )
                }
            }
        }
    }
}

@Composable
fun ContactComponent(
    modifier: Modifier = Modifier,
    label: String,
    number: String,
    testTag: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = DIM_8, end = DIM_4)
    ) {
        Text(
            modifier = Modifier
                .defaultMinSize(minWidth = 55.dp)
                .testTag("${testTag}_LABEL"),
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier
                .padding(start = DIM_4)
                .testTag("${testTag}_VALUE"),
            text = number,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

object AgencyComponentTestTags {
    private const val PREFIX = "AGENCY_"
    const val AGENCY_LAYOUT = "${PREFIX}LAYOUT"
    const val AGENCY_NAME = "${PREFIX}NAME"
    const val AGENCY_ADDRESS = "${PREFIX}ADDRESS"
}

object AgentComponentTestTags {
    private const val PREFIX = "AGENT_"
    const val AGENT_CARD = "${PREFIX}CARD"
    const val AGENT_NAME = "${PREFIX}NAME"
    const val AGENT_IMAGE = "${PREFIX}IMAGE"
    const val AGENT_MOBILE = "${PREFIX}MOBILE"
    const val AGENT_GENERAL = "${PREFIX}GENERAL"
    const val AGENT_FAX = "${PREFIX}FAX"
    const val AGENT_EMAIL = "${PREFIX}EMAIL"
}

@Preview(showBackground = true)
@Composable
fun AgentComponentPreview() {
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