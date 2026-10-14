package au.com.deanpike.listings.ui.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.listings.client.model.listing.response.Listing
import au.com.deanpike.listings.client.model.listing.response.Project
import au.com.deanpike.listings.client.model.listing.response.Property
import au.com.deanpike.listings.ui.list.ListingListScreenEvent
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
import org.maplibre.compose.camera.CameraFit
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.CameraUpdate
import org.maplibre.compose.expressions.dsl.asString
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.eq
import org.maplibre.compose.expressions.dsl.feature
import org.maplibre.compose.expressions.dsl.format
import org.maplibre.compose.expressions.dsl.image
import org.maplibre.compose.expressions.dsl.not
import org.maplibre.compose.expressions.dsl.offset
import org.maplibre.compose.expressions.dsl.span
import org.maplibre.compose.expressions.value.SymbolAnchor
import org.maplibre.compose.interaction.ClickResult
import org.maplibre.compose.interaction.MapInteractions
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.rememberMapState
import org.maplibre.compose.sources.GeoJsonData.Features
import org.maplibre.compose.sources.GeoJsonSource
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.DpPadding
import org.maplibre.spatialk.geojson.BoundingBox
import org.maplibre.spatialk.geojson.Feature
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Geometry
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position

@Composable
fun MapContent(
    listings: List<Listing>,
    onEvent: (ListingListScreenEvent) -> Unit = {}
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

    var clickedCluster by remember {
        mutableStateOf<Pair<GeoJsonSource, Feature<Geometry, JsonObject?>>?>(null)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val mapState = rememberMapState(
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
            initialCameraPosition = CameraPosition(
                center = Position(latitude = -25.6, longitude = 134.35),
                zoom = 2.8
            )
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
            ) {
                cluster = true
                clusterRadius = CLUSTER_RADIUS
                clusterMaxZoom = CLUSTER_MAX_ZOOM
            }
            val isCluster = feature.has(CLUSTER_POINT_COUNT_PROPERTY)
            CircleLayer(
                id = "listing-clusters",
                source = pinsSource,
                filter = isCluster,
                color = const(PIN_COLOR),
                radius = const(CLUSTER_MARKER_RADIUS),
                strokeColor = const(Color.White),
                strokeWidth = const(2.dp),
                onClick = { clickedFeatures ->
                    clickedFeatures.firstOrNull()?.let { clusterFeature ->
                        clickedCluster = pinsSource to clusterFeature
                    }
                    ClickResult.Consume
                }
            )
            SymbolLayer(
                id = "listing-cluster-counts",
                source = pinsSource,
                filter = isCluster,
                textField = format(span(feature[CLUSTER_POINT_COUNT_ABBREVIATED_PROPERTY].asString())),
                textFont = const(listOf(CLUSTER_COUNT_FONT)),
                textSize = const(14.sp),
                textColor = const(Color.White),
                textAllowOverlap = const(true),
                textIgnorePlacement = const(true)
            )
            val markerImage = image(
                value = painterResource(id = au.com.deanpike.listings.ui.R.drawable.map_marker),
                drawAsSdf = true
            )
            SymbolLayer(
                id = "listing-pins",
                source = pinsSource,
                filter = !isCluster,
                iconImage = markerImage,
                iconColor = const(PIN_COLOR),
                iconHaloColor = const(Color.White),
                iconHaloWidth = const(1.dp),
                iconAnchor = const(SymbolAnchor.Bottom),
                iconOffset = offset(0.dp, MARKER_SDF_PADDING),
                iconAllowOverlap = const(true),
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
            selectedListingId?.let { selectedId ->
                SymbolLayer(
                    id = "selected-listing-pin",
                    source = pinsSource,
                    filter = feature[PIN_LISTING_ID_PROPERTY].asString() eq const(selectedId.toString()),
                    iconImage = markerImage,
                    iconColor = const(SELECTED_PIN_COLOR),
                    iconHaloColor = const(Color.White),
                    iconHaloWidth = const(1.dp),
                    iconAnchor = const(SymbolAnchor.Bottom),
                    iconOffset = offset(0.dp, MARKER_SDF_PADDING),
                    iconAllowOverlap = const(true)
                )
            }
        }

        LaunchedEffect(mapState, clickedCluster) {
            val (source, clusterFeature) = clickedCluster ?: return@LaunchedEffect
            clickedCluster = null
            val center = (clusterFeature.geometry as? Point)?.coordinates ?: return@LaunchedEffect
            val zoom = mapState.style.sources[source]
                ?.getClusterExpansionZoom(clusterFeature)
                ?: return@LaunchedEffect
            mapState.animateCamera(
                CameraUpdate(
                    center = center,
                    zoom = zoom
                )
            )
        }

        LaunchedEffect(mapState, pins) {
            val bounds = toPinBounds(pins) ?: return@LaunchedEffect
            if (bounds.southwest == bounds.northeast) {
                mapState.animateCamera(
                    CameraUpdate(
                        center = bounds.southwest,
                        zoom = SINGLE_PIN_ZOOM
                    )
                )
            } else {
                mapState.animateCameraToBounds(
                    boundingBox = bounds,
                    fit = CameraFit(
                        fitPadding = DpPadding(
                            left = PIN_BOUNDS_PADDING,
                            top = PIN_BOUNDS_PADDING,
                            right = PIN_BOUNDS_PADDING,
                            bottom = PIN_BOUNDS_PADDING
                        )
                    )
                )
            }
        }

        val interactions = remember {
            MapInteractions {
                callbacks {
                    click {
                        onUnhandled {
                            selectedListingId = null
                            ClickResult.Pass
                        }
                    }
                }
            }
        }

        MaplibreMap(
            modifier = Modifier
                .fillMaxSize()
                .testTag(LISTING_MAP),
            state = mapState,
            interactions = interactions
        )

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
                    .clickable {
                        selectedListingCardInfo?.let { cardInfo ->
                            onEvent(
                                when (cardInfo.listingType) {
                                    ListingType.PROJECT -> ListingListScreenEvent.OnProjectSelected(
                                        id = cardInfo.listingId,
                                        address = cardInfo.address
                                    )
                                    else -> ListingListScreenEvent.OnPropertySelected(
                                        id = cardInfo.listingId,
                                        address = cardInfo.address
                                    )
                                }
                            )
                        }
                    }
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
                listingId = listing.id,
                address = listing.address,
                imageUrl = listing.listingImage,
                title = listing.headLine,
                listingType = listing.listingType
            )
            is Project -> MapSelectionCardInfo(
                listingId = listing.id,
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

internal fun toPinBounds(pins: List<MapPin>): BoundingBox? {
    if (pins.isEmpty()) {
        return null
    }
    return BoundingBox(
        southwest = Position(
            longitude = pins.minOf { it.position.longitude },
            latitude = pins.minOf { it.position.latitude }
        ),
        northeast = Position(
            longitude = pins.maxOf { it.position.longitude },
            latitude = pins.maxOf { it.position.latitude }
        )
    )
}

private const val SINGLE_PIN_ZOOM = 14.0
private val PIN_BOUNDS_PADDING = 48.dp
private const val PIN_LISTING_ID_PROPERTY = "listingId"
private const val PIN_LISTING_TYPE_PROPERTY = "listingType"
private val SELECTED_PIN_COLOR = Color.Red

// Drawing the marker as an SDF pads it by ceil(8dp * 0.75) on every side, so shift it down to put the tip on the coordinate
private val MARKER_SDF_PADDING = 6.dp
private const val CLUSTER_RADIUS = 50
private const val CLUSTER_MAX_ZOOM = 14
private val CLUSTER_MARKER_RADIUS = 16.dp
private const val CLUSTER_POINT_COUNT_PROPERTY = "point_count"
private const val CLUSTER_POINT_COUNT_ABBREVIATED_PROPERTY = "point_count_abbreviated"
// Must be a font served by the base style's glyphs
private const val CLUSTER_COUNT_FONT = "Noto Sans Bold"
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
    val listingId: Long,
    val address: String,
    val imageUrl: String?,
    val title: String?,
    val listingType: ListingType
)
