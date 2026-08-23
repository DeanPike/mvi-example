package au.com.deanpike.listings.ui.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Listing
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.list.ListingListScreenTestTags.LISTING_MAP
import au.com.deanpike.listings.ui.list.MapPin
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_4
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.asString
import org.maplibre.compose.expressions.dsl.case
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.feature
import org.maplibre.compose.expressions.dsl.switch
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData.Features
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.ClickResult
import org.maplibre.spatialk.geojson.Feature
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position

@Composable
fun MapContent(
    listings: List<Listing>
) {
    val pins = remember(listings) {
        toMapPins(listings)
    }

    var selectedListingId by remember {
        mutableStateOf<Long?>(null)
    }

    val selectedListingCardInfo = remember(listings, selectedListingId) {
        toMapSelectionCardInfo(listings, selectedListingId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MaplibreMap(
            modifier = Modifier
                .fillMaxSize()
                .testTag(LISTING_MAP),
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
            cameraState = rememberCameraState(
                firstPosition = CameraPosition(
                    target = Position(latitude = -25.6, longitude = 134.35),
                    zoom = 2.8
                )
            ),
            onMapClick = { _, _ ->
                selectedListingId = null
                ClickResult.Pass
            }
        ) {
            val pinsSource = rememberGeoJsonSource(
                data = Features(
                    FeatureCollection(
                        features = pins.map { pin ->
                            Feature<Point, JsonObject?>(
                                geometry = Point(pin.position),
                                properties = buildJsonObject {
                                    put(PIN_LISTING_ID_PROPERTY, pin.listingId.toString())
                                    put(PIN_LISTING_TYPE_PROPERTY, pin.listingType.name)
                                }
                            )
                        }
                    )
                )
            )
            CircleLayer(
                id = "listing-pins",
                source = pinsSource,
                color = const(PIN_COLOR),
                radius = const(8.dp),
                strokeColor = selectedListingId?.let { selectedId ->
                    switch(
                        input = feature[PIN_LISTING_ID_PROPERTY].asString(),
                        case(label = selectedId.toString(), output = const(Color.Green)),
                        fallback = const(Color.White)
                    )
                } ?: const(Color.White),
                strokeWidth = const(2.dp),
                onClick = { clickedFeatures ->
                    selectedListingId = clickedFeatures.firstOrNull()
                        ?.properties
                        ?.get(PIN_LISTING_ID_PROPERTY)
                        ?.jsonPrimitive
                        ?.content
                        ?.toLongOrNull()
                    ClickResult.Consume
                }
            )
        }

        AnimatedVisibility(
            visible = selectedListingCardInfo != null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(DIM_16)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(MapContentTestTags.LISTING_MAP_SELECTED_CARD),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier.padding(DIM_8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(72.dp)
                            .aspectRatio(1F)
                            .clip(RoundedCornerShape(DIM_8))
                            .testTag(MapContentTestTags.LISTING_MAP_SELECTED_CARD_IMAGE),
                        placeholder = painterResource(id = R.drawable.gallery_placeholder),
                        error = painterResource(id = R.drawable.gallery_placeholder),
                        model = ImageRequest.Builder(LocalContext.current)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .data(selectedListingCardInfo?.imageUrl)
                            .crossfade(true)
                            .error(R.drawable.gallery_placeholder)
                            .placeholder(R.drawable.gallery_placeholder)
                            .build(),
                        contentDescription = stringResource(id = R.string.property_image_description),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(start = DIM_16)
                    ) {
                        Text(
                            modifier = Modifier.testTag(MapContentTestTags.LISTING_MAP_SELECTED_CARD_LISTING_TYPE),
                            text = selectedListingCardInfo?.listingType?.name.orEmpty(),
                            color = Color.Black,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            modifier = Modifier.testTag(MapContentTestTags.LISTING_MAP_SELECTED_CARD_TITLE),
                            text = selectedListingCardInfo?.title.orEmpty(),
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = DIM_4)
                                .testTag(MapContentTestTags.LISTING_MAP_SELECTED_CARD_ADDRESS),
                            text = selectedListingCardInfo?.address.orEmpty(),
                            color = Color.Black,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

internal fun toMapSelectionCardInfo(listings: List<Listing>, selectedListingId: Long?): MapSelectionCardInfo? {
    return listings.firstOrNull { it.id == selectedListingId }?.let { listing ->
        when (listing) {
            is Property -> MapSelectionCardInfo(
                address = listing.address,
                imageUrl = listing.listingImage,
                title = listing.headLine,
                listingType = listing.listingType
            )
            is Project -> MapSelectionCardInfo(
                address = listing.address,
                imageUrl = listing.listingImage,
                title = listing.projectName,
                listingType = listing.listingType
            )
            else -> null
        }
    }
}

internal fun toMapPins(listings: List<Listing>): List<MapPin> {
    return listings.mapNotNull { listing ->
        val geoLocation = when (listing) {
            is Property -> listing.geoLocation
            is Project -> listing.geoLocation
            else -> null
        } ?: return@mapNotNull null
        MapPin(
            listingId = listing.id,
            listingType = listing.listingType,
            position = Position(longitude = geoLocation.longitude, latitude = geoLocation.latitude)
        )
    }
}

private const val PIN_LISTING_ID_PROPERTY = "listingId"
private const val PIN_LISTING_TYPE_PROPERTY = "listingType"
private val PIN_COLOR = Color.Green.copy(
    red = Color.Green.red * 0.76f,
    green = Color.Green.green * 0.76f,
    blue = Color.Green.blue * 0.76f
)

object MapContentTestTags {
    private const val PREFIX = "MAP_CONTENT_"
    const val LISTING_MAP_SELECTED_CARD = "${PREFIX}SELECTED_CARD"
    const val LISTING_MAP_SELECTED_CARD_IMAGE = "${PREFIX}SELECTED_CARD_IMAGE"
    const val LISTING_MAP_SELECTED_CARD_LISTING_TYPE = "${PREFIX}SELECTED_CARD_LISTING_TYPE"
    const val LISTING_MAP_SELECTED_CARD_TITLE = "${PREFIX}SELECTED_CARD_TITLE"
    const val LISTING_MAP_SELECTED_CARD_ADDRESS = "${PREFIX}SELECTED_CARD_ADDRESS"
}

internal data class MapSelectionCardInfo(
    val address: String,
    val imageUrl: String?,
    val title: String?,
    val listingType: ListingType
)
