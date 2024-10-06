package au.com.deanpike.ui.framework.robot

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.listings.ui.list.component.ProjectChildListItemComponent
import au.com.deanpike.listings.ui.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.listings.ui.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LIFECYCLE
import au.com.deanpike.listings.ui.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_PRICE
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
import org.junit.Assert

class ProjectChildListItemComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ProjectChildListItemComponentRobot, ProjectChildListItemComponentRobotInitData>(composeRule) {
    private var childId: Long = 0L
    var clickedId: Long? = null
        private set

    override fun setupComponent(data: ProjectChildListItemComponentRobotInitData?): ProjectChildListItemComponentRobot {
        composeRule.setContent {
            MviExampleTheme {
                ProjectChildListItemComponent(
                    projectChild = data!!.projectChild,
                    onProjectChildClicked = {
                        clickedId = it
                    }
                )
            }
        }

        return this
    }

    override fun assertLayoutDisplayed(): ProjectChildListItemComponentRobot {
        Assert.assertEquals("This function has not been implemented. Use assertChildLayoutDisplayed", true, false)
        return this
    }

    fun forChild(id: Long): ProjectChildListItemComponentRobot {
        childId = id
        return this
    }

    fun assertChildLayoutDisplayed(): ProjectChildListItemComponentRobot {
        composeRule.assertTagDisplayed("${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId")
        return this
    }

    fun assertPriceDisplayed(
        text: String
    ): ProjectChildListItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${PROJECT_CHILD_LIST_ITEM_PRICE}_$childId",
            text = text
        )
        return this
    }

    fun assertLifecycleDisplayed(
        text: String
    ): ProjectChildListItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${PROJECT_CHILD_LIST_ITEM_LIFECYCLE}_$childId",
            text = text
        )
        return this
    }

    fun clickCard(): ProjectChildListItemComponentRobot {
        composeRule.clickOn(tag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId")
        return this
    }

    fun assertBedroomDisplayed(bedrooms: String): ProjectChildListItemComponentRobot {
        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BEDROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BEDROOMS_ICON"))

        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BEDROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BEDROOMS_TEXT") and hasText(bedrooms))
        return this
    }

    fun assertBathroomDisplayed(bathrooms: String): ProjectChildListItemComponentRobot {
        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BATHROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BATHROOMS_ICON"))

        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BATHROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BATHROOMS_TEXT") and hasText(bathrooms))
        return this
    }

    fun assertCarSpacesDisplayed(carSpaces: String): ProjectChildListItemComponentRobot {
        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_CAR_SPACES))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_CAR_SPACES_ICON"))

        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$childId", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_CAR_SPACES))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_CAR_SPACES_TEXT") and hasText(carSpaces))
        return this
    }
}

data class ProjectChildListItemComponentRobotInitData(
    val projectChild: ProjectChild
) : TestRobotInitData
