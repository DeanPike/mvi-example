package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_CARD_LAYOUT
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_EMAIL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_FAX
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_GENERAL
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_IMAGE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_MOBILE
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_NAME
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import coil.compose.AsyncImage

@Composable
fun AgentComponent(
    agents: List<Agent>
) {
    agents.forEachIndexed { index, agent ->
        Card(
            modifier = Modifier
                .padding(top = DIM_8, start = DIM_16, end = DIM_16)
                .fillMaxWidth(),
            border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.outline),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(DIM_4)
                    .testTag("${AGENT_CARD_LAYOUT}_$index")
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = DIM_8),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = DIM_8)
                            .testTag("${AGENT_NAME}_$index"),
                        text = agent.name ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row {
                        Column {
                            ContactComponent(
                                label = stringResource(id = R.string.mobile),
                                value = agent.phoneNumbers.firstOrNull {
                                    it.type == PhoneNumberType.MOBILE
                                }?.number ?: "",
                                testTag = "${AGENT_MOBILE}_$index"
                            )
                            ContactComponent(
                                label = stringResource(id = R.string.general),
                                value = agent.phoneNumbers.firstOrNull {
                                    it.type == PhoneNumberType.GENERAL
                                }?.number ?: "",
                                testTag = "${AGENT_GENERAL}_$index"
                            )
                            ContactComponent(
                                label = stringResource(id = R.string.fax),
                                value = agent.phoneNumbers.firstOrNull {
                                    it.type == PhoneNumberType.FAX
                                }?.number ?: "",
                                testTag = "${AGENT_FAX}_$index"
                            )
                            ContactComponent(
                                label = stringResource(id = R.string.email),
                                value = agent.emailAddress ?: "",
                                testTag = "${AGENT_EMAIL}_$index"
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .size(60.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    agent.imageUrl?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(top = DIM_4, end = DIM_4)
                                .size(60.dp)
                                .clip(CircleShape)
                                .testTag("${AGENT_IMAGE}_$index"),
                            model = it,
                            contentDescription = stringResource(id = R.string.agent_photo),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = au.com.deanpike.uishared.R.drawable.gallery_placeholder),
                            fallback = painterResource(id = au.com.deanpike.uishared.R.drawable.gallery_placeholder),
                            error = painterResource(id = au.com.deanpike.uishared.R.drawable.gallery_placeholder),
                        )
                    }
                }
            }

        }
    }
}

object AgentComponentTestTags {
    private const val PREFIX = "AGENT_"
    const val AGENT_CARD_LAYOUT = "${PREFIX}CARD_LAYOUT"
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
    MviExampleTheme {
        AgentComponent(
            agents = listOf(
                Agent(
                    id = "1697102",
                    address = """Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164""",
                    name = "Riccardo Romolo with a lot of extra text to make sure that the name wraps",
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
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AgentComponentNoImagePreview() {
    MviExampleTheme {
        AgentComponent(
            agents = listOf(
                Agent(
                    id = "1697102",
                    address = """Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164""",
                    name = "Riccardo Romolo",
                    imageUrl = null,
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
                )
            )
        )
    }
}