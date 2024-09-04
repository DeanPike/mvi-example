package au.com.deanpike.detail.ui.framework.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.detail.client.model.detail.ProjectChild
import au.com.deanpike.detail.ui.project.ProjectChildComponent
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_IMAGE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LAYOUT
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_LIFECYCLE
import au.com.deanpike.detail.ui.project.ProjectChildComponentTestTags.PROJECT_CHILD_PRICE
import au.com.deanpike.uishared.theme.MviExampleTheme
import au.com.deanpike.uitestshared.base.TestRobotBase
import au.com.deanpike.uitestshared.base.TestRobotInitData
import au.com.deanpike.uitestshared.util.assertTagDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed
import au.com.deanpike.uitestshared.util.clickOn
import org.assertj.core.api.Assertions.assertThat

class ProjectChildComponentRobot(private val composeRule: ComposeContentTestRule) : TestRobotBase<ProjectChildComponentRobot, ProjectChildComponentRobotInitData>(composeRule) {
    private var clickedProjectChildId: Long? = null

    override fun setupComponent(data: ProjectChildComponentRobotInitData?): ProjectChildComponentRobot {
        clickedProjectChildId = null

        composeRule.setContent {
            MviExampleTheme {
                ProjectChildComponent(
                    child = ProjectChild(
                        id = 2019256252,
                        bedroomCount = 3,
                        bathroomCount = 2,
                        carSpaceCount = 1,
                        price = "Starting from $2,000,000 with extra data",
                        propertyTypeDescription = "newApartments",
                        propertyUrl = "https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252",
                        propertyImage = "https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875",
                        lifecycleStatus = "New Home"
                    ),
                    onProjectChildClicked = {
                        clickedProjectChildId = it
                    }
                )
            }
        }
        return this
    }

    override fun assertLayoutDisplayed(): ProjectChildComponentRobot {
        composeRule.assertTagDisplayed(PROJECT_CHILD_LAYOUT)
        return this
    }

    fun assertClickedProjectChildId(id: Long): ProjectChildComponentRobot {
        assertThat(clickedProjectChildId).isEqualTo(id)
        return this
    }

    fun assertProjectChildImageDisplayed(): ProjectChildComponentRobot {
        composeRule.assertTagDisplayed(PROJECT_CHILD_IMAGE)
        return this
    }

    fun assertLifecycle(lifecycle: String): ProjectChildComponentRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_CHILD_LIFECYCLE,
            text = lifecycle
        )
        return this
    }

    fun assertPriceDisplayed(price: String): ProjectChildComponentRobot {
        composeRule.assertTextDisplayed(
            tag = PROJECT_CHILD_PRICE,
            text = price
        )
        return this
    }

    fun clickProjectChild(): ProjectChildComponentRobot {
        composeRule.clickOn(PROJECT_CHILD_LAYOUT)
        return this
    }
}

data class ProjectChildComponentRobotInitData(
    val child: ProjectChild
) : TestRobotInitData