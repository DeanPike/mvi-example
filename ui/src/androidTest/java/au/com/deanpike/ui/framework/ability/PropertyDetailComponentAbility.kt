package au.com.deanpike.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.ui.R
import au.com.deanpike.ui.screen.shared.DetailListItemTestTags
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class PropertyDetailComponentAbility(private val composeTestRule: ComposeContentTestRule) {
    private val ability = DetailItemComponentAbility(composeTestRule)

    fun assertGroupDisplayed(position: Int) {
        composeTestRule.assertTagDisplayed("${DetailListItemTestTags.DETAIL_ITEM_GROUP}_$position")
    }

    fun assertBedroomNotDisplayed(position: Int) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_BEDROOMS}_$position")
    }

    fun assertBedroomDisplayed(
        position: Int,
        text: String
    ) {
        ability.assertTextDisplayed(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            text = text
        )
        ability.assertIconDisplayed(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            drawable = R.drawable.bed_outline
        )
        ability.assertIconContentDescription(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            text = "Number of bedrooms"
        )
    }

    fun assertBathroomNotDisplayed(position: Int) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_BATHROOMS}_$position")
    }

    fun assertBathroomDisplayed(
        position: Int,
        text: String
    ) {
        ability.assertTextDisplayed(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            text = text
        )
        ability.assertIconDisplayed(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            drawable = R.drawable.bath_outline
        )
        ability.assertIconContentDescription(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            text = "Number of bathrooms"
        )
    }

    fun assertCarSpaceNotDisplayed(position: Int) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES}_$position")
    }

    fun assertCarSpaceDisplayed(
        position: Int,
        text: String
    ) {
        ability.assertTextDisplayed(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            text = text
        )
        ability.assertIconDisplayed(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            drawable = R.drawable.car_outline
        )
        ability.assertIconContentDescription(
            position = position,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            text = "Number of parking spaces"
        )
    }

    fun assertDwellingTypeNotDisplayed(position: Int) {
        composeTestRule.assertTagDoesNotExist("${DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE}_$position")
    }

    fun assertDwellingTypeDisplayed(
        position: Int,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE}_$position",
            text = text
        )

    }
}