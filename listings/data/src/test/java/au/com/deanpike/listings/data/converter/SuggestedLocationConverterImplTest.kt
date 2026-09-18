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
            assertThat(displayName).isEqualTo("Bondi Beach, NSW 2026")
            assertThat(name).isEqualTo("Bondi Beach")
            assertThat(state).isEqualTo("NSW")
            assertThat(regionName).isEqualTo("Sydney")
            assertThat(areaName).isEqualTo("Eastern Suburbs")
            assertThat(postCode).isEqualTo("2026")
            assertThat(suburbId).isEqualTo("12345")
            assertThat(nameSlug).isEqualTo("bondi-beach-nsw-2026")
            assertThat(category).isEqualTo("Suburb")
            assertThat(group).isEqualTo("Suburbs")
        }
    }

    @Test
    fun `convert suggested location with null fields`() {
        val suggestedLocation = SuggestedLocation()

        val location = SuggestedLocationConverterImpl().convertSuggestedLocation(suggestedLocation)

        with(location) {
            assertThat(displayName).isNull()
            assertThat(name).isNull()
            assertThat(state).isNull()
            assertThat(regionName).isNull()
            assertThat(areaName).isNull()
            assertThat(postCode).isNull()
            assertThat(suburbId).isNull()
            assertThat(nameSlug).isNull()
            assertThat(category).isNull()
            assertThat(group).isNull()
        }
    }
}
