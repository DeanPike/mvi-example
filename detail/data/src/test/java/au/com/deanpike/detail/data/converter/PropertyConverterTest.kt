package au.com.deanpike.detail.data.converter

import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.detail.client.model.detail.PropertyDetail
import au.com.deanpike.detail.client.model.type.MediaType
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import au.com.deanpike.network.model.external.detail.ListingDetail
import com.google.gson.Gson
import java.io.InputStreamReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PropertyConverterTest {
    private val gson = Gson()

    private val converter = PropertyConverter()

    @Test
    fun `create property`() {
        val data = readData()
        val property = converter.convertDetail(data)

        assertThat(property).isInstanceOf(PropertyDetail::class.java)

        with(property as PropertyDetail) {
            assertThat(id).isEqualTo(2018868051)
            assertThat(listingType).isEqualTo(ListingType.PROPERTY)
            assertThat(address).isEqualTo("2 Glenton Street, Abbotsbury")
            assertThat(headline).isEqualTo("Neat Corner Block Home on 657sqm")
            assertThat(description).isEqualTo("Rare opportunity to secure this single storey corner block home on 657sqm. Perfect for a downsizer or someone looking for a canvas to knock down & build their dream home! With four bedrooms, two bathrooms & a two car garage this home will cater your needs.\r\n\r\nOutside, the low-maintenance yard is perfect for those who want to spend more time enjoying their home and less time maintaining it. With plenty of natural light throughout the home. Dont miss this chance to get into the Abbotsbury market!\r\n\r\nWhat the dwelling offers;\r\n• Four well appointed bedrooms all with built in wardrobes\r\n• Two Bathrooms (ensuite to main)\r\n• Double garage\r\n• Split system air conditioning \r\n• Separate & spacious kitchen and dining area\r\n• Large outdoor alfresco over looking backyard\r\n• Granny Flat potential (STCA)")
            assertThat(lifecycleStatus).isEqualTo("Sold")

            assertThat(media.count()).isEqualTo(13)
            assertThat(media[0].mediaType).isEqualTo(MediaType.PHOTO)
            assertThat(media[0].url).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2018868051_1_1_231030_075432-w5000-h3333")
            assertThat(media[12].mediaType).isEqualTo(MediaType.FLOOR_PLAN)
            assertThat(media[12].url).isEqualTo("https://bucket-api.domain.com.au/v1/bucket/image/2018868051_13_3_231030_075432-w2550-h3300")

            assertThat(advertiser!!.id).isEqualTo(2373)
            assertThat(advertiser!!.name).isEqualTo("Ray White Wetherill Park")
            assertThat(advertiser!!.address).isEqualTo("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")
            assertThat(advertiser!!.logoUrl).isEqualTo("https://images.domain.com.au/img/Agencys/2373/logo_2373.jpg?buster=2024-05-20")
            assertThat(advertiser!!.agencyBannerImageUrl).isEqualTo("https://images.domain.com.au/img/Agencys/2373/banner_2373.jpg?buster=2024-05-20")
            assertThat(advertiser!!.preferredColorHex).isEqualTo("#FEE536")

            assertThat(advertiser!!.agencyListingContacts.count()).isEqualTo(2)
            assertThat(advertiser!!.agencyListingContacts[0].id).isEqualTo("1697102")
            assertThat(advertiser!!.agencyListingContacts[0].address).isEqualTo("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")
            assertThat(advertiser!!.agencyListingContacts[0].name).isEqualTo("Riccardo Romolo")
            assertThat(advertiser!!.agencyListingContacts[0].imageUrl).isEqualTo("https://images.domain.com.au/img/2373/contact_1697102.jpeg?buster=2024-05-20")
            assertThat(advertiser!!.agencyListingContacts[0].emailAddress).isEqualTo("riccardo.romolo@raywhite.com")

            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers.count()).isEqualTo(3)
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[0].type).isEqualTo(PhoneNumberType.MOBILE)
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[0].label).isEqualTo("Mobile")
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[0].number).isEqualTo("0452 184 976")
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[1].type).isEqualTo(PhoneNumberType.GENERAL)
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[1].label).isEqualTo("General")
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[1].number).isEqualTo("02 9609 7099")
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[2].type).isEqualTo(PhoneNumberType.FAX)
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[2].label).isEqualTo("Fax")
            assertThat(advertiser!!.agencyListingContacts[0].phoneNumbers[2].number).isEqualTo("02 9609 2370")

            assertThat(advertiser!!.agencyListingContacts[1].id).isEqualTo("1350251")
            assertThat(advertiser!!.agencyListingContacts[1].address).isEqualTo("Shop 1H, 1183-1187 The Horsley Drive\nWetherill Park NSW 2164")
            assertThat(advertiser!!.agencyListingContacts[1].name).isEqualTo("Marcus Biasetto")
            assertThat(advertiser!!.agencyListingContacts[1].imageUrl).isEqualTo("https://images.domain.com.au/img/2373/contact_1350251.png?buster=2024-05-20")
            assertThat(advertiser!!.agencyListingContacts[1].emailAddress).isEqualTo("marcus.biasetto@raywhite.com")

            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers.count()).isEqualTo(3)
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[0].type).isEqualTo(PhoneNumberType.MOBILE)
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[0].label).isEqualTo("Mobile")
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[0].number).isEqualTo("0414 246 947")
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[1].type).isEqualTo(PhoneNumberType.GENERAL)
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[1].label).isEqualTo("General")
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[1].number).isEqualTo("02 9609 7099")
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[2].type).isEqualTo(PhoneNumberType.FAX)
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[2].label).isEqualTo("Fax")
            assertThat(advertiser!!.agencyListingContacts[1].phoneNumbers[2].number).isEqualTo("02 9609 2370")

            assertThat(schools.count()).isEqualTo(2)
            assertThat(schools[0].id).isEqualTo(41609)
            assertThat(schools[0].address).isEqualTo("Bossley Park NSW 2176")
            assertThat(schools[0].yearRange).isEqualTo("7-12")
            assertThat(schools[0].name).isEqualTo("Bossley Park High School")
            assertThat(schools[0].educationLevel).isEqualTo("Secondary")
            assertThat(schools[0].gender).isEqualTo("Co-ed")
            assertThat(schools[0].system).isEqualTo("Government")

            assertThat(schools[1].id).isEqualTo(41513)
            assertThat(schools[1].address).isEqualTo("Edensor Park NSW 2176")
            assertThat(schools[1].yearRange).isEqualTo("K-6")
            assertThat(schools[1].name).isEqualTo("Edensor Park Public School")
            assertThat(schools[1].educationLevel).isEqualTo("Primary")
            assertThat(schools[1].gender).isEqualTo("Co-ed")
            assertThat(schools[1].system).isEqualTo("Government")

            assertThat(dwellingType).isEqualTo("House")
            assertThat(price).isEqualTo("$1,320,000")
            assertThat(bedroomCount).isEqualTo(4)
            assertThat(bathroomCount).isEqualTo(2)
            assertThat(carSpaceCount).isEqualTo(1)
            assertThat(dateSold).isEqualTo("2023-11-25T00:00:00")
            assertThat(saleType).isEqualTo("Auction")
        }
    }

    private fun readData(): ListingDetail {
        val data = ClassLoader.getSystemResourceAsStream("raw/property_details.json")?.let { InputStreamReader(it, "UTF-8").readText() }
        return gson.fromJson<ListingDetail>(data, ListingDetail::class.java)
    }
}