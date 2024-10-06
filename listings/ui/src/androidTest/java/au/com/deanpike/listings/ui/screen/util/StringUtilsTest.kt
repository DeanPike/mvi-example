package au.com.deanpike.listings.ui.screen.util


import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.listings.ui.util.StringUtils
import au.com.deanpike.uitestshared.base.UiUnitTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StringUtilsTest : UiUnitTestBase() {
    @Test
    fun get_description_for_status() {
        with(composeTestRule) {
            setContent {
                assertThat(StringUtils.getStatusDescription(type = StatusType.BUY)).isEqualTo("Buy")
                assertThat(StringUtils.getStatusDescription(type = StatusType.SOLD)).isEqualTo("Sold")
                assertThat(StringUtils.getStatusDescription(type = StatusType.RENT)).isEqualTo("Rent")
            }
        }
    }

    @Test
    fun get_description_for_listing_type() {
        with(composeTestRule) {
            setContent {
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.ALL)).isEqualTo("All")
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.HOUSE)).isEqualTo("House")
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.TOWNHOUSE)).isEqualTo("Townhouse")
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.APARTMENT_UNIT_FLAT)).isEqualTo("Apartment / Unit / Flat")
            }
        }
    }
}