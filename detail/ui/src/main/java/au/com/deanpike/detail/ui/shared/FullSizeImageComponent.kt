package au.com.deanpike.detail.ui.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.detail.ui.shared.FullSizeImageTestTags.FULL_SIZE_IMAGE_BACK_BUTTON
import au.com.deanpike.detail.ui.shared.FullSizeImageTestTags.FULL_SIZE_IMAGE_IMAGE
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.base.drawableTestTag
import au.com.deanpike.uishared.theme.MviExampleTheme
import me.saket.telephoto.zoomable.EnabledZoomGestures
import me.saket.telephoto.zoomable.coil3.ZoomableAsyncImage
import me.saket.telephoto.zoomable.rememberZoomableImageState

@Composable
fun FullSizeImageComponent(
    url: String,
    onBackClicked: () -> Unit = {}
) {

    val zoomableState = rememberZoomableImageState()
    ZoomableAsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .testTag(FULL_SIZE_IMAGE_IMAGE),
        model = url,
        state = zoomableState,
        gestures = EnabledZoomGestures.ZoomAndPan,
        contentDescription = stringResource(id = R.string.property_image),
    )

    Box {
        IconButton(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .align(Alignment.TopStart),
            onClick = { onBackClicked() },
            shape = CircleShape,
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4F)
            )
        ) {
            Icon(
                modifier = Modifier
                    .drawableTestTag(
                        tag = FULL_SIZE_IMAGE_BACK_BUTTON,
                        id = R.drawable.arrow_back_24
                    ),
                painter = painterResource(R.drawable.arrow_back_24),
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.background,
            )
        }
    }
}

object FullSizeImageTestTags {
    private const val PREFIX = "FULL_SIZE_IMAGE_COMPONENT"
    const val FULL_SIZE_IMAGE_IMAGE = "${PREFIX}_IMAGE"
    const val FULL_SIZE_IMAGE_BACK_BUTTON = "${PREFIX}_BACK_BUTTON"
}

@Preview
@Composable
fun ImageComponentListPreview() {
    MviExampleTheme {
        FullSizeImageComponent(
            url = """https://rimh2.domain.com.au/50GrKuJN8m91NGgoT4um2phagCU=/1440x0/filters:quality(70):format(jpeg):no_upscale()/https://bucket-api.domain.com.au/v1/bucket/image/2020651916_1_1_260303_113932-w1080-h1080"""
        )
    }
}