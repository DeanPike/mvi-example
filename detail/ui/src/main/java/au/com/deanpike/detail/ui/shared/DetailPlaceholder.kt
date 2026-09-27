package au.com.deanpike.detail.ui.shared

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Shown in the detail pane of the list/detail layout before a property or project is selected.
 */
@Composable
fun DetailPlaceholder() {
    Text(text = "Select a property or project to see details")
}
