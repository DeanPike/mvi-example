package au.com.deanpike.ui.screen.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import au.com.deanpike.client.model.PersonDTO
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.component.PersonListItemTesTags.PERSON_LIST_ITEM_AGE
import au.com.deanpike.ui.screen.list.component.PersonListItemTesTags.PERSON_LIST_ITEM_AGE_LABEL
import au.com.deanpike.ui.screen.list.component.PersonListItemTesTags.PERSON_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.PersonListItemTesTags.PERSON_LIST_ITEM_NAME
import au.com.deanpike.ui.screen.list.component.PersonListItemTesTags.PERSON_LIST_ITEM_NAME_LABEL
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme
import java.util.UUID

@Composable
fun PersonListItem(
    position: Int,
    person: PersonDTO,
    onItemClicked: (UUID) -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .shadow(elevation = DIM_4)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White)
            .padding(DIM_8)
            .clickable {
                person.id?.let {
                    onItemClicked(it)
                }
            }
            .testTag(PERSON_LIST_ITEM_LAYOUT)
    ) {
        val (nameLabelRef, nameRef, ageLabelRef, ageRef) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(nameLabelRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    baseline.linkTo(nameRef.baseline)
                }
                .testTag("${PERSON_LIST_ITEM_NAME_LABEL}_$position"),
            text = stringResource(id = R.string.name),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        Text(
            text = "${person.name} ${person.surname}",
            modifier = Modifier
                .constrainAs(nameRef) {
                    top.linkTo(parent.top)
                    start.linkTo(nameLabelRef.end, DIM_8)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .testTag("${PERSON_LIST_ITEM_NAME}_$position"),
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Text(
            modifier = Modifier
                .constrainAs(ageLabelRef) {
                    start.linkTo(parent.start)
                    top.linkTo(nameRef.bottom)
                    baseline.linkTo(ageRef.baseline)
                }
                .padding(top = DIM_4)
                .testTag("${PERSON_LIST_ITEM_AGE_LABEL}_$position"),
            text = stringResource(id = R.string.age),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        Text(
            modifier = Modifier
                .constrainAs(ageRef) {
                    start.linkTo(nameRef.start)
                    top.linkTo(nameRef.bottom)
                }
                .padding(top = DIM_4)
                .testTag("${PERSON_LIST_ITEM_AGE}_$position"),
            text = "${person.age}"
        )
    }
}

object PersonListItemTesTags {
    private const val PREFIX = "PERSON_LIST_ITEM_"
    const val PERSON_LIST_ITEM_LAYOUT = "${PREFIX}LAYOUT"
    const val PERSON_LIST_ITEM_NAME_LABEL = "${PREFIX}NAME_LABEL"
    const val PERSON_LIST_ITEM_NAME = "${PREFIX}NAME"
    const val PERSON_LIST_ITEM_AGE_LABEL = "${PREFIX}AGE_LABEL"
    const val PERSON_LIST_ITEM_AGE = "${PREFIX}AGE"
}

@Composable
@Preview
fun PersonListItemPreview() {
    MviExampleTheme {
        PersonListItem(
            position = 0,
            person = PersonDTO(
                id = UUID.randomUUID(),
                name = "Name",
                surname = "Surname",
                age = 24
            )
        )
    }
}

@Composable
@Preview
fun PersonWithLongNameListItemPreview() {
    MviExampleTheme {
        PersonListItem(
            position = 0,
            person = PersonDTO(
                id = UUID.randomUUID(),
                name = "An unusually long first name 1234567890 1234567890 1234567890",
                surname = "An unusually long second name 1234567890 1234567890 1234567890",
                age = 24
            )
        )
    }
}