package au.com.deanpike.uishared.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.uishared.R
import au.com.deanpike.uishared.component.PriceComponentTestTags.PRICE_COMPONENT_DATA
import au.com.deanpike.uishared.component.PriceComponentTestTags.PRICE_COMPONENT_LABEL
import au.com.deanpike.uishared.theme.Dimension.DIM_16
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun PriceComponent(
    modifier: Modifier = Modifier,
    price: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.testTag(PRICE_COMPONENT_LABEL),
            text = stringResource(R.string.price),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .padding(end = DIM_16, start = DIM_8)
                .testTag(PRICE_COMPONENT_DATA),
            text = price,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

object PriceComponentTestTags {
    private const val PREFIX = "PRICE_COMPONENT_"
    const val PRICE_COMPONENT_LABEL = "${PREFIX}HEADING"
    const val PRICE_COMPONENT_DATA = "${PREFIX}DATA"
}

@Preview(showBackground = true)
@Composable
fun PriceComponentPreview() {
    MviExampleTheme {
        PriceComponent(price = "$1,000,000")
    }
}