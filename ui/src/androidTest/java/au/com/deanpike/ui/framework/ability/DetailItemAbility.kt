package au.com.deanpike.ui.framework.ability

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uitestshared.util.assertContentDescription
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class DetailItemAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertTextDisplayed(
        position: Int,
        tag: String,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${tag}_TEXT_$position",
            text = text
        )
    }

    fun assertIconContentDescription(
        position: Int,
        tag: String,
        text: String
    ) {
        composeTestRule.assertContentDescription(
            tag = "${tag}_ICON_$position",
            text = text
        )
    }

    fun assertIconDisplayed(
        position: Int,
        tag: String,
        @DrawableRes drawable: Int
    ) {
        composeTestRule.assertDrawableDisplayed(
            tag = "${tag}_ICON_$position",
            drawable = drawable
        )
    }
}