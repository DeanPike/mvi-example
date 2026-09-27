package au.com.deanpike.navigation.scene

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavMetadataKey
import androidx.navigation3.runtime.contains
import androidx.navigation3.runtime.metadata
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND

class ListDetailScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val listEntry: NavEntry<T>,
    val detailEntry: NavEntry<T>?,
    val detailPlaceholder: @Composable () -> Unit,
) : Scene<T> {

    override val entries: List<NavEntry<T>> = listOfNotNull(listEntry, detailEntry)
    override val content: @Composable (() -> Unit) = {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(0.4f)) {
                listEntry.Content()
            }
            Column(modifier = Modifier.weight(0.6f)) {
                detailEntry?.Content() ?: detailPlaceholder()
            }
        }
    }
}

/**
 * @param detailPlaceholder shown in the detail pane when a list is on top of the back stack with
 * nothing selected yet.
 */
@Composable
fun <T : Any> rememberListDetailSceneStrategy(
    detailPlaceholder: @Composable () -> Unit
): ListDetailSceneStrategy<T> {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    return remember(windowSizeClass, detailPlaceholder) {
        ListDetailSceneStrategy(windowSizeClass, detailPlaceholder)
    }
}

class ListDetailSceneStrategy<T : Any>(
    val windowSizeClass: WindowSizeClass,
    val detailPlaceholder: @Composable () -> Unit
) : SceneStrategy<T> {

    companion object {

        /**
         * Helper function to add metadata to a [NavEntry] indicating it can be displayed
         * as a list in the [ListDetailScene].
         */
        fun listPane() = metadata {
            put(ListKey, true)
        }

        /**
         * Helper function to add metadata to a [NavEntry] indicating it can be displayed
         * as a list in the [ListDetailScene].
         */
        fun detailPane() = metadata {
            put(DetailKey, true)
        }
    }

    object ListKey : NavMetadataKey<Boolean>
    object DetailKey : NavMetadataKey<Boolean>

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)) {
            return null
        }

        val lastEntry = entries.lastOrNull() ?: return null

        // A list on top with nothing selected: show it beside the placeholder detail pane.
        if (lastEntry.metadata.contains(ListKey)) {
            return ListDetailScene(
                key = lastEntry.contentKey,
                previousEntries = entries.dropLast(1),
                listEntry = lastEntry,
                detailEntry = null,
                detailPlaceholder = detailPlaceholder
            )
        }

        val detailEntry = lastEntry.takeIf { it.metadata.contains(DetailKey) } ?: return null
        val listEntry = entries.findLast { it.metadata.contains(ListKey) } ?: return null

        // Keyed by the list entry so moving between the placeholder and a detail keeps the list pane.
        return ListDetailScene(
            key = listEntry.contentKey,
            previousEntries = entries.dropLast(1),
            listEntry = listEntry,
            detailEntry = detailEntry,
            detailPlaceholder = detailPlaceholder
        )
    }
}