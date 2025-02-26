package au.com.deanpike.detail.ui.project

import androidx.lifecycle.viewModelScope
import au.com.deanpike.commonshared.util.ResponseWrapper
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.detail.client.usecase.ProjectDetailUseCase
import au.com.deanpike.uishared.base.BaseViewModel
import au.com.deanpike.uishared.base.ScreenStateType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val useCase: ProjectDetailUseCase
) : BaseViewModel<ProjectDetailScreenEvent, ProjectDetailScreenState, ProjectDetailScreenEffect>() {
    override fun createInitialState() = ProjectDetailScreenState()

    override fun handleEvent(event: ProjectDetailScreenEvent) {
        when (event) {
            is ProjectDetailScreenEvent.Initialise -> {
                initialiseScreen(event.projectId)
            }
            is ProjectDetailScreenEvent.OnRetryClicked -> {
                uiState.projectId?.let {
                    initialiseScreen(it)
                }
            }
        }
    }

    private fun initialiseScreen(projectId: Long) {
        viewModelScope.launch(dispatcher.getIoDispatcher()) {
            when (val response = useCase.getProjectDetails(projectId)) {
                is ResponseWrapper.Success -> {
                    setState {
                        copy(
                            projectId = projectId,
                            projectDetail = response.data,
                            screenState = ScreenStateType.SUCCESS
                        )
                    }

                }
                is ResponseWrapper.Error -> {
                    setState {
                        copy(
                            projectId = projectId,
                            projectDetail = null,
                            screenState = ScreenStateType.ERROR
                        )
                    }
                }
            }
        }
    }
}