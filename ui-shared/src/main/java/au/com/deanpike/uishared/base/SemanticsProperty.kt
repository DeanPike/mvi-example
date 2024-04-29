package au.com.deanpike.uishared.base

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag

val DrawablePropertyKey = SemanticsPropertyKey<Int>(
    name = "DrawableProperty",
    mergePolicy = { parentValue, _ ->
        // Never merge TestTags, to avoid leaking internal test tags to parents.
        parentValue
    }
)
var SemanticsPropertyReceiver.drawableId by DrawablePropertyKey

@Stable
fun Modifier.drawableTestTag(
    tag: String,
    id: Int
) = semantics(
    properties = {
        testTag = tag
        drawableId = id
    }
)