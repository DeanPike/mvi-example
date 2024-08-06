package au.com.deanpike.mviexample.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import au.com.deanpike.detail.ui.project.ProjectDetailScreen
import au.com.deanpike.detail.ui.property.PropertyDetailScreen
import au.com.deanpike.ui.screen.list.ListingListScreen
import au.com.deanpike.uishared.theme.Dimension

@Composable
fun MviMediumApp() {
    val unselectedListingId = -1L

    var selectedProjectId by rememberSaveable {
        mutableLongStateOf(unselectedListingId)
    }
    var selectedProjectChildId by rememberSaveable {
        mutableLongStateOf(unselectedListingId)
    }
    var selectedPropertyId by rememberSaveable {
        mutableLongStateOf(unselectedListingId)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.3F)) {
            ListingListScreen(
                onPropertyClicked = { propertyId ->
                    selectedPropertyId = propertyId
                    selectedProjectId = unselectedListingId
                    selectedProjectChildId = unselectedListingId
                },
                onProjectClicked = { projectId ->
                    selectedProjectId = projectId
                    selectedPropertyId = unselectedListingId
                    selectedProjectChildId = unselectedListingId
                },
                onListingsReset = {
                    selectedProjectId = -1
                    selectedProjectChildId = -1
                    selectedPropertyId = -1
                }
            )
        }
        Spacer(modifier = Modifier.width(Dimension.DIM_16))
        Column(modifier = Modifier.fillMaxWidth()) {
            if (selectedProjectId != unselectedListingId && selectedProjectChildId == unselectedListingId) {
                ProjectDetailScreen(
                    projectId = selectedProjectId.toInt(),
                    onCloseClicked = {
                        selectedProjectChildId = unselectedListingId
                    },
                    onProjectChildClicked = { propertyId ->
                        selectedPropertyId = unselectedListingId
                        selectedProjectChildId = propertyId
                    }
                )
            }
            if (selectedPropertyId != unselectedListingId || selectedProjectChildId != unselectedListingId) {
                PropertyDetailScreen(
                    propertyId = if (selectedPropertyId != unselectedListingId) selectedPropertyId.toInt() else selectedProjectChildId.toInt(),
                    onCloseClicked = {
                        if (selectedProjectChildId != unselectedListingId) {
                            selectedProjectChildId = unselectedListingId
                        } else {
                            selectedPropertyId = unselectedListingId
                        }
                    }
                )
            }
        }
    }
}