package au.com.deanpike.uishared.util

import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark Mode",
    showBackground = false,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO
)
annotation class ThemePreviews