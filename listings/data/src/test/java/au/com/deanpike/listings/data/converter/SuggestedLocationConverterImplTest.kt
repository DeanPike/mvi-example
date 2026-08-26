package au.com.deanpike.listings.data.converter

import au.com.deanpike.network.model.external.suggestedlocation.SuggestedLocation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SuggestedLocationConverterImplTest {

    @Test
    fun `convert suggested location`() {
        val suggestedLocation = SuggestedLocation(
            displayName = "Bondi Beach, NSW 2026",
            name = "Bondi Beach",
            state = "NSW",
            regionName = "Sydney",
            areaName = "Eastern Suburbs",
            postCode = "2026",
            suburbId = "12345",
            nameSlug = "bondi-beach-nsw-2026",
            category = "Suburb",
            group = "Suburbs"
        )

        val location = SuggestedLocationConverterImpl().convertSuggestedLocation(suggestedLocation)

        with(location) {
            assertThat(nameSlug).isEqualTo("bondi-beach-nsw-2026")
            assertThat(displayName).isEqualTo("Bondi Beach, NSW 2026")
        }
    }

    @Test
    fun `convert suggested location with null fields`() {
        val suggestedLocation = SuggestedLocation()

        val location = SuggestedLocationConverterImpl().convertSuggestedLocation(suggestedLocation)

        with(location) {
            assertThat(nameSlug).isNull()
            assertThat(displayName).isNull()
        }
    }
}
