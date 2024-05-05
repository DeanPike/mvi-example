package au.com.deanpike.ui.framework.ability.shared

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags
import au.com.deanpike.ui.screen.shared.DetailListItemTestTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.scrollTo

class PropertyDetailComponentAbility(private val composeTestRule: ComposeContentTestRule) {
    private val ability = DetailItemComponentAbility(composeTestRule)

    fun scrollTo(parentPosition: Int, childPosition: Int) {
        composeTestRule.scrollTo("${ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT}_${parentPosition}_${childPosition}")
    }

    fun assertGroupDisplayed(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.assertTagDisplayed("${DetailListItemTestTags.DETAIL_ITEM_GROUP}_${parentPosition}_${position}")
    }

    fun assertBedroomNotDisplayed(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_BEDROOMS}_${parentPosition}_${position}")
    }

    fun assertBedroomDisplayed(
        parentPosition: Int,
        position: Int,
        text: String
    ) {
        ability.assertTextDisplayed(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            text = text
        )
        ability.assertIconDisplayed(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            drawable = R.drawable.bed_outline
        )
        ability.assertIconContentDescription(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            text = "Number of bedrooms"
        )
    }

    fun assertBathroomNotDisplayed(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_BATHROOMS}_${parentPosition}_${position}")
    }

    fun assertBathroomDisplayed(
        parentPosition: Int,
        position: Int,
        text: String
    ) {
        ability.assertTextDisplayed(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            text = text
        )
        ability.assertIconDisplayed(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            drawable = R.drawable.bath_outline
        )
        ability.assertIconContentDescription(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            text = "Number of bathrooms"
        )
    }

    fun assertCarSpaceNotDisplayed(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES}_${parentPosition}_${position}")
    }

    fun assertCarSpaceDisplayed(
        parentPosition: Int,
        position: Int,
        text: String
    ) {
        ability.assertTextDisplayed(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            text = text
        )
        ability.assertIconDisplayed(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            drawable = R.drawable.car_outline
        )
        ability.assertIconContentDescription(
            parentPosition = parentPosition,
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            text = "Number of parking spaces"
        )
    }

    fun assertDwellingTypeNotDisplayed(
        parentPosition: Int,
        position: Int
    ) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE}_${parentPosition}_${position}")
    }

    fun assertDwellingTypeDisplayed(
        parentPosition: Int,
        position: Int,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE}_${parentPosition}_${position}",
            text = text
        )

    }
}