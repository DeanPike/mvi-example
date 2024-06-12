package au.com.deanpike.detail.data.converter

import au.com.deanpike.datashared.type.ListingType
import au.com.deanpike.commonshared.type.MediaType
import au.com.deanpike.detail.client.model.type.PhoneNumberType
import com.google.gson.Gson
import java.io.InputStreamReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectConverterTest {
    private val gson = Gson()

    @Test
    fun `create project`() {
        val data = readData()
        val project = ProjectConverter.convertDetail(data)

        with(project) {
            assertThat(id).isEqualTo(6303)
            assertThat(listingType).isEqualTo(ListingType.PROJECT)
            assertThat(address).isEqualTo("13 Crown Street, Wollongong")
            assertThat(headline).isEqualTo("Grand Opening Saturday - Easterly Wollongong")
            assertThat(description).isEqualTo(
                "Introducing ‘Easterly’, a prestigious collection of 21 elegantly designed 2 & 3 bedroom residences, accompanied by 3 spectacular penthouses, located at 13 Crown Street, Wollongong. \n\nAwaken to the gentle rhythm of waves, bask in sweeping ocean panoramas, and savour the refined sophistication of your impeccably crafted residence. Positioned directly opposite WIN Stadium and mere steps from the beach, Easterly offers an unparalleled coastal lifestyle.\n\nExplore Wollongong's premier attractions nearby, from the historic Flagstaff Point Lighthouse to the serene Wollongong Botanic Gardens, & Blue Mile Pathway. Delight in the vibrant local dining scene, with charming cafes, stylish bars, and gourmet restaurants just moments away, enriching the allure of this iconic location.\n\nDeveloped by Level 33, Easterly epitomizes coastal living with its commitment to privacy, exclusivity, and unparalleled craftsmanship. In collaboration with Turner Architects, the architectural design seamlessly integrates the natural allure of the ocean with the enduring strength of the land, capturing the essence of coastal beauty in both structure and interiors.\n\nExperience coastal living redefined with Easterly's expansive balconies, floor-to-ceiling windows, premium Gaggenau appliances, luxurious stone finishes, bespoke joinery, spa-inspired bathrooms with freestanding baths, and tranquil bedrooms finished with custom wardrobes.\n\nWelcome to elevated coastal living – welcome to Easterly."
            )
            assertThat(dwellingType).isEqualTo("New Apartments / Off the Plan")
            assertThat(projectName).isEqualTo("Easterly Wollongong")
            assertThat(projectColorHex).isEqualTo("#C8BDB1")
            assertThat(projectLogoImageUrl).isEqualTo("""https://images.domain.com.au/img/Agencys/devproject/logo_6303_240522_025453""")
            assertThat(showroomAddress).isEqualTo("49 Denison Street, Wollongong")
        }

        assertThat(project.media.count()).isEqualTo(15)
        assertThat(project.media[0].mediaType).isEqualTo(MediaType.PHOTO)
        assertThat(project.media[0].url).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/915bae83-b78c-449d-a646-8c29a786e0c6-w2500-h906""")
        assertThat(project.media[14].mediaType).isEqualTo(MediaType.PHOTO)
        assertThat(project.media[14].url).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/2019256302_9_1_240521_034536-w1571-h2160""")

        with(project.advertiser!!) {
            assertThat(id).isEqualTo(37445)
            assertThat(name).isEqualTo("Level 33")
            assertThat(address).isEqualTo("30a Eva St\nRiverwood NSW 2210")
            assertThat(logoUrl).isEqualTo("""https://images.domain.com.au/img/Agencys/37445/logo_37445.png?buster=2024-05-23""")
            assertThat(agencyBannerImageUrl).isNull()
            assertThat(preferredColorHex).isEqualTo("#000000")
        }

        assertThat(project.advertiser!!.agencyListingContacts.count()).isEqualTo(1)
        with(project.advertiser!!.agencyListingContacts[0]) {
            assertThat(id).isEqualTo("1925335")
            assertThat(address).isEqualTo("30a Eva St\nRiverwood NSW 2210")
            assertThat(name).isEqualTo("Property Team")
            assertThat(imageUrl).isEqualTo("""https://images.domain.com.au/img/37445/contact_1925335.png?buster=2024-05-23""")
            assertThat(emailAddress).isEqualTo("will.wehbe@level33.com.au")

            assertThat(phoneNumbers.count()).isEqualTo(2)
            assertThat(phoneNumbers[0].type).isEqualTo(PhoneNumberType.MOBILE)
            assertThat(phoneNumbers[0].label).isEqualTo("Mobile")
            assertThat(phoneNumbers[0].number).isEqualTo("1300 112 712")
            assertThat(phoneNumbers[1].type).isEqualTo(PhoneNumberType.GENERAL)
            assertThat(phoneNumbers[1].label).isEqualTo("General")
            assertThat(phoneNumbers[1].number).isEqualTo("(02) 8199 1144")
        }

        assertThat(project.schools.count()).isEqualTo(2)
        with(project.schools) {
            assertThat(this[0].id).isEqualTo(42932)
            assertThat(this[0].address).isEqualTo("Fairy Meadow NSW 2519")
            assertThat(this[0].yearRange).isEqualTo("7-12")
            assertThat(this[0].name).isEqualTo("Keira High School")
            assertThat(this[0].educationLevel).isEqualTo("Secondary")
            assertThat(this[0].gender).isEqualTo("Co-ed")
            assertThat(this[0].system).isEqualTo("Government")

            assertThat(this[1].id).isEqualTo(42848)
            assertThat(this[1].address).isEqualTo("Wollongong NSW 2500")
            assertThat(this[1].yearRange).isEqualTo("K-6")
            assertThat(this[1].name).isEqualTo("Wollongong Public School")
            assertThat(this[1].educationLevel).isEqualTo("Primary")
            assertThat(this[1].gender).isEqualTo("Co-ed")
            assertThat(this[1].system).isEqualTo("Government")
        }

        assertThat(project.childListings.count()).isEqualTo(2)
        with(project.childListings) {
            assertThat(this[0].id).isEqualTo(2019256252)
            assertThat(this[0].bedroomCount).isEqualTo(2)
            assertThat(this[0].bathroomCount).isEqualTo(2)
            assertThat(this[0].carSpaceCount).isEqualTo(1)
            assertThat(this[0].price).isEqualTo("Starting from $2,000,000")
            assertThat(this[0].propertyTypeDescription).isEqualTo("newApartments")
            assertThat(this[0].propertyUrl).isEqualTo("""https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252""")
            assertThat(this[0].propertyImage).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875""")
            assertThat(this[0].lifecycleStatus).isEqualTo("New Home")

            assertThat(this[1].id).isEqualTo(2019256302)
            assertThat(this[1].bedroomCount).isEqualTo(3)
            assertThat(this[1].bathroomCount).isEqualTo(2)
            assertThat(this[1].carSpaceCount).isEqualTo(1)
            assertThat(this[1].price).isEqualTo("Starting from $3,250,000")
            assertThat(this[1].propertyTypeDescription).isEqualTo("newApartments")
            assertThat(this[1].propertyUrl).isEqualTo("""https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256302""")
            assertThat(this[1].propertyImage).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/2019256252_5_1_240521_034450-w2500-h1468""")
            assertThat(this[1].lifecycleStatus).isEqualTo("New Home")

        }
    }

    private fun readData(): au.com.deanpike.network.model.external.detail.ProjectDetail {
        val data = ClassLoader.getSystemResourceAsStream("raw/project_details.json")?.let { InputStreamReader(it, "UTF-8").readText() }
        return gson.fromJson(data, au.com.deanpike.network.model.external.detail.ProjectDetail::class.java)
    }
}