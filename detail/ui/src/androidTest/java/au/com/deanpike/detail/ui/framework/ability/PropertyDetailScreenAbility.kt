package au.com.deanpike.detail.ui.framework.ability

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAILS_LAYOUT
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_ADDRESS
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_CLOSE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_DESCRIPTION
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_HEADLINE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_IMAGE_PAGER
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PRICE
import au.com.deanpike.detail.ui.property.PropertyDetailScreenTestTags.PROPERTY_DETAIL_PROGRESS
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.DetailListItemTestTags
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_DWELLING_TYPE
import au.com.deanpike.uitestshared.ability.DetailItemComponentAbility
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTagDoesNotExist
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class PropertyDetailScreenAbility(private val composeTestRule: ComposeContentTestRule) {
    private val detailItemAbility = DetailItemComponentAbility(composeTestRule)

    fun assertScreenDisplayed() {
        composeTestRule.waitUntilTagExists(PROPERTY_DETAILS_LAYOUT, 1000)
        composeTestRule.assertTagDisplayed(PROPERTY_DETAILS_LAYOUT)
    }

    fun assertProgressDisplayed() {
        composeTestRule.assertTagDisplayed(PROPERTY_DETAIL_PROGRESS)
    }

    fun assertProgressNotDisplayed() {
        composeTestRule.assertTagDoesNotExist(PROPERTY_DETAIL_PROGRESS)
    }

    fun assertCloseIconDisplayed() {
        composeTestRule.assertTagDisplayed(PROPERTY_DETAIL_CLOSE)
    }

    fun assertImagesDisplayed() {
        composeTestRule.assertTagDisplayed(PROPERTY_DETAIL_IMAGE_PAGER)
    }

    fun assertPriceDisplayed(price: String) {
        composeTestRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_PRICE,
            text = price
        )
    }

    fun assertAddressDisplayed(address: String) {
        composeTestRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_ADDRESS,
            text = address
        )
    }

    fun assertBedroomDisplayed(
        text: String
    ) {
        detailItemAbility.assertTextDisplayed(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            text = text
        )
        detailItemAbility.assertIconDisplayed(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            drawable = R.drawable.bed_outline
        )
        detailItemAbility.assertIconContentDescription(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_BEDROOMS,
            text = "Number of bedrooms"
        )
    }

    fun assertBathroomDisplayed(
        text: String
    ) {
        detailItemAbility.assertTextDisplayed(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            text = text
        )
        detailItemAbility.assertIconDisplayed(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            drawable = R.drawable.bath_outline
        )
        detailItemAbility.assertIconContentDescription(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_BATHROOMS,
            text = "Number of bathrooms"
        )
    }

    fun assertCarSpaceDisplayed(
        text: String
    ) {
        detailItemAbility.assertTextDisplayed(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            text = text
        )
        detailItemAbility.assertIconDisplayed(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            drawable = R.drawable.car_outline
        )
        detailItemAbility.assertIconContentDescription(
            parentPosition = 0,
            position = 0,
            tag = DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES,
            text = "Number of parking spaces"
        )
    }

    fun assertDwellingDisplayed(dwellingType: String) {
        composeTestRule.assertTextDisplayed(
            tag = "${DETAIL_ITEM_DWELLING_TYPE}_0_0",
            text = dwellingType
        )
    }

    fun assertHeadlineDisplayed(headline: String) {
        composeTestRule.assertTextDisplayed(
            tag = PROPERTY_DETAIL_HEADLINE,
            text = headline
        )
    }

    fun assertDescriptionDisplayed() {
        composeTestRule.assertTagDisplayed(tag = PROPERTY_DETAIL_DESCRIPTION)
    }

    fun clickCloseIcon() {
        composeTestRule.clickOn(PROPERTY_DETAIL_CLOSE)
    }
}