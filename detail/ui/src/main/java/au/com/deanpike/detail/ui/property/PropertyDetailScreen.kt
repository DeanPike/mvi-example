package au.com.deanpike.detail.ui.property

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PropertyDetailScreen(
    viewModel: PropertyDetailViewModel = viewModel(),
    propertyId: Int
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(PropertyDetailScreenEvent.Initialise(propertyId = propertyId))
    }
}

@Composable
fun PropertyDetailScreenContent(){

}