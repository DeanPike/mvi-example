package au.com.deanpike.listings.ui.util

import au.com.deanpike.listings.client.type.DwellingType
import au.com.deanpike.listings.client.type.StatusType
import au.com.deanpike.uitestshared.base.RobolectricTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StringUtilsTest : RobolectricTestBase() {

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
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.ALL)).isEqualTo("All property types")
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.HOUSE)).isEqualTo("House")
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.TOWNHOUSE)).isEqualTo("Townhouse")
                assertThat(StringUtils.getPropertyTypeDescription(type = DwellingType.APARTMENT_UNIT_FLAT)).isEqualTo("Apartment / Unit / Flat")
            }
        }
    }

    @Test
    fun given_all_parameters_provided_then_details_text_contains_all_parts() {
        val result = StringUtils.createDetailsText(
            dwellingType = "House",
            numberOfBedrooms = 3,
            numberOfBathrooms = 2,
            numberOfCarSpaces = 1
        )
        assertThat(result).isEqualTo("House • 3 Bed • 2 Bath • 1 Car")
    }

    @Test
    fun given_all_parameters_are_null_then_details_text_is_empty() {
        val result = StringUtils.createDetailsText(
            dwellingType = null,
            numberOfBedrooms = null,
            numberOfBathrooms = null,
            numberOfCarSpaces = null
        )
        assertThat(result).isEmpty()
    }

    @Test
    fun given_only_dwellingType_provided_then_details_text_contains_only_dwelling_type() {
        val result = StringUtils.createDetailsText(
            dwellingType = "Apartment",
            numberOfBedrooms = null,
            numberOfBathrooms = null,
            numberOfCarSpaces = null
        )
        assertThat(result).isEqualTo("Apartment")
    }

    @Test
    fun given_only_numberOfBedrooms_provided_then_details_text_contains_only_bedrooms() {
        val result = StringUtils.createDetailsText(
            dwellingType = null,
            numberOfBedrooms = 4,
            numberOfBathrooms = null,
            numberOfCarSpaces = null
        )
        assertThat(result).isEqualTo("4 Bed")
    }

    @Test
    fun given_only_numberOfBathrooms_provided_then_details_text_contains_only_bathrooms() {
        val result = StringUtils.createDetailsText(
            dwellingType = null,
            numberOfBedrooms = null,
            numberOfBathrooms = 2,
            numberOfCarSpaces = null
        )
        assertThat(result).isEqualTo("2 Bath")
    }

    @Test
    fun given_only_numberOfCarSpaces_provided_then_details_text_contains_only_car_spaces() {
        val result = StringUtils.createDetailsText(
            dwellingType = null,
            numberOfBedrooms = null,
            numberOfBathrooms = null,
            numberOfCarSpaces = 3
        )
        assertThat(result).isEqualTo("3 Car")
    }

    @Test
    fun given_dwellingType_and_bedrooms_provided_then_details_text_contains_both__separated_by_bullet() {
        val result = StringUtils.createDetailsText(
            dwellingType = "Townhouse",
            numberOfBedrooms = 2,
            numberOfBathrooms = null,
            numberOfCarSpaces = null
        )
        assertThat(result).isEqualTo("Townhouse • 2 Bed")
    }

    @Test
    fun given_bedrooms_and_bathrooms_provided_then_details_text_contains_both_in_correct_order() {
        val result = StringUtils.createDetailsText(
            dwellingType = null,
            numberOfBedrooms = 3,
            numberOfBathrooms = 1,
            numberOfCarSpaces = null
        )
        assertThat(result).isEqualTo("3 Bed • 1 Bath")
    }

    @Test
    fun given_zero_values_provided_then_details_text_includes_zero_values() {
        val result = StringUtils.createDetailsText(
            dwellingType = "House",
            numberOfBedrooms = 0,
            numberOfBathrooms = 0,
            numberOfCarSpaces = 0
        )
        assertThat(result).isEqualTo("House • 0 Bed • 0 Bath • 0 Car")
    }
}