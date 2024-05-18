package au.com.deanpike.ui.screen.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = viewModel()
) {

    Text(text = "Text")
}