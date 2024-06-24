package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import au.com.deanpike.detail.client.model.detail.Agent
import au.com.deanpike.detail.client.model.detail.PhoneNumber
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.detail.ui.R
import au.com.deanpike.detail.ui.shared.AgentComponentTestTags.AGENT_CARD
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
    position: Int,
    agent: Agent
) {
    Card(
        modifier = Modifier
            .padding(top = DIM_8, start = DIM_16, end = DIM_16)
            .testTag("${AGENT_CARD}_$position"),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Gray.copy(alpha = 0.05F)
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
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
                agent.phoneNumbers.firstOrNull {
                    it.type == PhoneNumberType.GENERAL
                }?.let {
                    ContactComponent(
                        label = stringResource(id = R.string.general),
                        number = it.number ?: "",
                        testTag = "${AGENT_GENERAL}_$position"
                    )
                }
                agent.phoneNumbers.firstOrNull {
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
    MviExampleTheme {
        AgentComponent(
            position = 0,
            agent = Agent(
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
            )
        )
    }
}