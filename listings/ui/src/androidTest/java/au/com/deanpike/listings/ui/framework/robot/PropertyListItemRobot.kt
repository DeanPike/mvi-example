package au.com.deanpike.listings.ui.framework.robot

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.list.component.PropertyListItem
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags
import au.com.deanpike.listings.ui.list.component.PropertyListItemTesTags.PROPERTY_LIST_ITEM_LAYOUT
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BATHROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_BEDROOMS
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_CAR_SPACES
import au.com.deanpike.uishared.component.DetailListItemTestTags.DETAIL_ITEM_GROUP
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import au.com.deanpike.uitestshared.util.waitUntilTagExists

class PropertyListItemRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<PropertyListItemRobot, PropertyListItemRobotInitData>(composeRule) {
    var clickedId: Long? = null
        private set

    override fun setupComponent(data: PropertyListItemRobotInitData?): PropertyListItemRobot {
        composeRule.setContent {
            MviExampleTheme {
                PropertyListItem(
                    property = data!!.property,
                    onItemClicked = {
                        clickedId = it
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): PropertyListItemRobot {
        composeRule.assertTagDisplayed(PROPERTY_LIST_ITEM_LAYOUT)
        return this
    }

    fun assertPropertyImageDisplayed(): PropertyListItemRobot {
        composeRule.assertTagDisplayed(tag = PropertyListItemTesTags.PROPERTY_LIST_ITEM_PROPERTY_IMAGE)
        return this
    }

    fun assertPriceDisplayed(price: String): PropertyListItemRobot {
        composeRule.assertTextDisplayed(tag = PropertyListItemTesTags.PROPERTY_LIST_ITEM_PRICE, text = price)
        return this
    }

    fun assertHeadlineDisplayed(headline: String): PropertyListItemRobot {
        composeRule.assertTextDisplayed(tag = PropertyListItemTesTags.PROPERTY_LIST_ITEM_HEADLINE, text = headline)
        return this
    }

    fun assertAddressDisplayed(address: String): PropertyListItemRobot {
        composeRule.assertTextDisplayed(tag = PropertyListItemTesTags.PROPERTY_LIST_ITEM_ADDRESS, text = address)
        return this
    }

    fun clickProperty(): PropertyListItemRobot {
        composeRule.clickOn(tag = PROPERTY_LIST_ITEM_LAYOUT)
        return this
    }

    fun assertBedroomDisplayed(bedrooms: String): PropertyListItemRobot {
        composeRule.onNodeWithTag(testTag = PROPERTY_LIST_ITEM_LAYOUT, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BEDROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BEDROOMS_ICON"))

        composeRule.onNodeWithTag(testTag = PROPERTY_LIST_ITEM_LAYOUT, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BEDROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BEDROOMS_TEXT") and hasText(bedrooms))
        return this
    }

    fun assertBathroomDisplayed(bathrooms: String): PropertyListItemRobot {
        composeRule.onNodeWithTag(testTag = PROPERTY_LIST_ITEM_LAYOUT, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BATHROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BATHROOMS_ICON"))

        composeRule.onNodeWithTag(testTag = PROPERTY_LIST_ITEM_LAYOUT, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BATHROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BATHROOMS_TEXT") and hasText(bathrooms))
        return this
    }

    fun assertCarSpacesDisplayed(carSpaces: String): PropertyListItemRobot {
        composeRule.onNodeWithTag(testTag = PROPERTY_LIST_ITEM_LAYOUT, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_CAR_SPACES))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_CAR_SPACES_ICON"))

        composeRule.onNodeWithTag(testTag = PROPERTY_LIST_ITEM_LAYOUT, useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_CAR_SPACES))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_CAR_SPACES_TEXT") and hasText(carSpaces))
        return this
    }

    fun waitForLayoutToBeDisplayed(): PropertyListItemRobot {
        composeRule.waitUntilTagExists(PROPERTY_LIST_ITEM_LAYOUT)
        return this
    }
}

data class PropertyListItemRobotInitData(
    val property: Property
) : TestRobotInitData