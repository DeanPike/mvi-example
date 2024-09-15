package au.com.deanpike.ui.framework.robot

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import au.com.deanpike.listings.client.model.listing.response.ProjectChild
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponent
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LAYOUT
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_LIFECYCLE
import au.com.deanpike.ui.screen.list.component.ProjectChildListItemComponentTestTags.PROJECT_CHILD_LIST_ITEM_PRICE
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

    fun assertChildLayoutDisplayed(id: Long): ProjectChildListItemComponentRobot {
        composeRule.assertTagDisplayed("${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id")
        return this
    }

    fun assertPriceDisplayed(
        id: Long,
        text: String
    ): ProjectChildListItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${PROJECT_CHILD_LIST_ITEM_PRICE}_$id",
            text = text
        )
        return this
    }

    fun assertLifecycleDisplayed(
        id: Long,
        text: String
    ): ProjectChildListItemComponentRobot {
        composeRule.assertTextDisplayed(
            tag = "${PROJECT_CHILD_LIST_ITEM_LIFECYCLE}_$id",
            text = text
        )
        return this
    }

    fun clickCard(id: Long): ProjectChildListItemComponentRobot {
        composeRule.clickOn(tag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id")
        return this
    }

    fun assertBedroomDisplayed(
        id: Long,
        bedrooms: String
    ): ProjectChildListItemComponentRobot {
        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BEDROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BEDROOMS_ICON"))

        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BEDROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BEDROOMS_TEXT") and hasText(bedrooms))
        return this
    }

    fun assertBathroomDisplayed(
        id: Long,
        bathrooms: String
    ): ProjectChildListItemComponentRobot {
        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BATHROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BATHROOMS_ICON"))

        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_BATHROOMS))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_BATHROOMS_TEXT") and hasText(bathrooms))
        return this
    }

    fun assertCarSpacesDisplayed(
        id: Long,
        carSpaces: String
    ): ProjectChildListItemComponentRobot {
        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id", useUnmergedTree = true)
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_GROUP))
            .onChildren()
            .filterToOne(hasTestTag(DETAIL_ITEM_CAR_SPACES))
            .onChildren()
            .assertAny(hasTestTag("DETAIL_ITEM_CAR_SPACES_ICON"))

        composeRule.onNodeWithTag(testTag = "${PROJECT_CHILD_LIST_ITEM_LAYOUT}_$id", useUnmergedTree = true)
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
