package au.com.deanpike.uishared.base

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag

val ListingIdPropertyKey = SemanticsPropertyKey<Long>(
    name = "ListingIdProperty",
    mergePolicy = { parentValue, _ ->
        // Never merge TestTags, to avoid leaking internal test tags to parents.
        parentValue
    }
)
var SemanticsPropertyReceiver.listingPropertyId by ListingIdPropertyKey

@Stable
fun Modifier.listingIdTestTag(
    tag: String,
    id: Long
) = semantics(
    properties = {
        testTag = tag
        listingPropertyId = id
    }
)