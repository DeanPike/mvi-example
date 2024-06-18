package au.com.deanpike.detail.ui.project

import au.com.deanpike.detail.client.model.detail.ProjectDetail
import au.com.deanpike.uishared.base.ScreenStateType
import au.com.deanpike.uishared.base.UiEffect
import au.com.deanpike.uishared.base.UiEvent
import au.com.deanpike.uishared.base.UiState

data class ProjectDetailScreenState(
    val screenState: ScreenStateType = ScreenStateType.INITIAL,
    val projectId: Int? = null,
    val projectDetail: ProjectDetail? = null
) : UiState

sealed class ProjectDetailScreenEvent : UiEvent {
    data class Initialise(
        val projectId: Int
    ) : ProjectDetailScreenEvent()

    data object OnRetryClicked : ProjectDetailScreenEvent()
}

sealed class ProjectDetailScreenEffect : UiEffect {

}