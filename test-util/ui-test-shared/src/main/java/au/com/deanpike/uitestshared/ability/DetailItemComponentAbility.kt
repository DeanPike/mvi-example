package au.com.deanpike.uitestshared.ability

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import au.com.deanpike.uitestshared.util.assertContentDescription
import au.com.deanpike.uitestshared.util.assertDrawableDisplayed
import au.com.deanpike.uitestshared.util.assertTextDisplayed

class DetailItemComponentAbility(private val composeTestRule: ComposeContentTestRule) {
    fun assertTextDisplayed(
        parentPosition: Int,
        position: Int,
        tag: String,
        text: String
    ) {
        composeTestRule.assertTextDisplayed(
            tag = "${tag}_TEXT_${parentPosition}_${position}",
            text = text
        )
    }

    fun assertIconContentDescription(
        parentPosition: Int,
        position: Int,
        tag: String,
        text: String
    ) {
        composeTestRule.assertContentDescription(
            tag = "${tag}_ICON_${parentPosition}_${position}",
            text = text
        )
    }

    fun assertIconDisplayed(
        parentPosition: Int,
        position: Int,
        tag: String,
        @DrawableRes drawable: Int
    ) {
        composeTestRule.assertDrawableDisplayed(
            tag = "${tag}_ICON_${parentPosition}_${position}",
            drawable = drawable
        )
    }
}