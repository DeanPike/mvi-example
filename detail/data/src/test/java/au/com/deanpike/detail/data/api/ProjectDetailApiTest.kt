package au.com.deanpike.detail.data.api

import au.com.deanpike.network.api.ProjectDetailApi
import com.google.gson.GsonBuilder
import java.io.InputStreamReader
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectDetailApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ProjectDetailApi
    private lateinit var jsonResponse: String
    private val gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ProjectDetailApi::class.java)
        jsonResponse = readFile()
    }

    @Test
    fun `get project details`() = runTest {
        val res = MockResponse()
        res.setBody(jsonResponse)
        server.enqueue(res)

        val data = api.getProjectDetails(
            contentType = "application/json",
            id = "1234"
        )
        server.takeRequest()

        with(data) {
            assertThat(id).isEqualTo(6303)
            assertThat(headline).isEqualTo("Grand Opening Saturday - Easterly Wollongong")
            assertThat(endDate).isEqualTo("2025-05-21T00:00:00")
            assertThat(description).isEqualTo(
                "Introducing ‘Easterly’, a prestigious collection of 21 elegantly designed 2 & 3 bedroom residences, accompanied by 3 spectacular penthouses, located at 13 Crown Street, Wollongong. \n\nAwaken to the gentle rhythm of waves, bask in sweeping ocean panoramas, and savour the refined sophistication of your impeccably crafted residence. Positioned directly opposite WIN Stadium and mere steps from the beach, Easterly offers an unparalleled coastal lifestyle.\n\nExplore Wollongong's premier attractions nearby, from the historic Flagstaff Point Lighthouse to the serene Wollongong Botanic Gardens, & Blue Mile Pathway. Delight in the vibrant local dining scene, with charming cafes, stylish bars, and gourmet restaurants just moments away, enriching the allure of this iconic location.\n\nDeveloped by Level 33, Easterly epitomizes coastal living with its commitment to privacy, exclusivity, and unparalleled craftsmanship. In collaboration with Turner Architects, the architectural design seamlessly integrates the natural allure of the ocean with the enduring strength of the land, capturing the essence of coastal beauty in both structure and interiors.\n\nExperience coastal living redefined with Easterly's expansive balconies, floor-to-ceiling windows, premium Gaggenau appliances, luxurious stone finishes, bespoke joinery, spa-inspired bathrooms with freestanding baths, and tranquil bedrooms finished with custom wardrobes.\n\nWelcome to elevated coastal living – welcome to Easterly."
            )
            assertThat(address).isEqualTo("13 Crown Street, Wollongong")
            assertThat(listingType).isEqualTo("project")

            assertThat(data.media.count()).isEqualTo(16)
            assertThat(data.media[0].mediaType).isEqualTo("video")
            assertThat(data.media[0].type).isEqualTo("youtube")
            assertThat(data.media[0].imageUrl).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/915bae83-b78c-449d-a646-8c29a786e0c6-w2500-h906""")
            assertThat(data.media[15].mediaType).isEqualTo("image")
            assertThat(data.media[15].type).isEqualTo("photo")
            assertThat(data.media[15].imageUrl).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/2019256302_9_1_240521_034536-w1571-h2160""")

            assertThat(additionalFeatures.count()).isEqualTo(8)
            assertThat(additionalFeatures[0]).isEqualTo("Stunning Water Views")
            assertThat(additionalFeatures[7]).isEqualTo("Only Two Units Per Floor")

            assertThat(dwellingType).isEqualTo("New Apartments / Off the Plan")
            assertThat(phoneEnquiryPreference).isFalse()
            assertThat(projectBrochureUrl).isEqualTo("""https://s3-ap-southeast-2.amazonaws.com/p-listing-documents/ProjectBrochure_agentadmin%2337445%2307823a4f-e846-4b25-8c19-e20ee454f6ec%23.pdf""")
            assertThat(projectWebsiteUrl).isEqualTo("""https://easterlyresidences.com.au/""")
            assertThat(projectName).isEqualTo("Easterly Wollongong")
            assertThat(projectCategory).isEqualTo("houseAndLand")
            assertThat(totalNumberResidences).isEqualTo(21)
            assertThat(projectColorHex).isEqualTo("#C8BDB1")
            assertThat(projectLogoImageUrl).isEqualTo("""https://images.domain.com.au/img/Agencys/devproject/logo_6303_240522_025453""")
            assertThat(isPremiumProject).isTrue()
            assertThat(showroomAddress).isEqualTo("49 Denison Street, Wollongong")
        }

        with(data.advertiser) {
            assertThat(id).isEqualTo(37445)
            assertThat(name).isEqualTo("Level 33")
            assertThat(type).isEqualTo("Agent")
            assertThat(address).isEqualTo("30a Eva St\nRiverwood NSW 2210")
            assertThat(images!!.logoUrl).isEqualTo("""https://images.domain.com.au/img/Agencys/37445/logo_37445.png?buster=2024-05-23""")
            assertThat(preferredColorHex).isEqualTo("#000000")
            assertThat(agencyListingContacts.count()).isEqualTo(1)
            assertThat(domainUrl).isEqualTo("level33-37445")
        }

        with(data.advertiser.agencyListingContacts[0]) {
            assertThat(agentId).isEqualTo("1925335")
            assertThat(address).isEqualTo("30a Eva St\nRiverwood NSW 2210")
            assertThat(firstName).isEqualTo("Property")
            assertThat(lastName).isEqualTo("Team")
            assertThat(displayFullName).isEqualTo("Property Team")
            assertThat(imageUrl).isEqualTo("""https://images.domain.com.au/img/37445/contact_1925335.png?buster=2024-05-23""")
            assertThat(emailAddress).isEqualTo("will.wehbe@level33.com.au")
            assertThat(phoneNumbers.count()).isEqualTo(3)
            assertThat(phoneNumbers[0].type).isEqualTo("Mobile")
            assertThat(phoneNumbers[0].displayLabel).isEqualTo("Mobile")
            assertThat(phoneNumbers[0].number).isEqualTo("1300 112 712")
            assertThat(phoneNumbers[1].type).isEqualTo("General")
            assertThat(phoneNumbers[1].displayLabel).isEqualTo("General")
            assertThat(phoneNumbers[1].number).isEqualTo("(02) 8199 1144")
            assertThat(phoneNumbers[2].type).isEqualTo("Fax")
            assertThat(phoneNumbers[2].displayLabel).isEqualTo("Fax")
            assertThat(phoneNumbers[2].number).isNull()
        }

        assertThat(data.schools.count()).isEqualTo(2)
        with(data.schools) {
            assertThat(this[0].id).isEqualTo(42932)
            assertThat(this[0].acaraId).isEqualTo(42932)
            assertThat(this[0].domainId).isEqualTo(8892)
            assertThat(this[0].address).isEqualTo("Fairy Meadow NSW 2519")
            assertThat(this[0].yearRange).isEqualTo("7-12")
            assertThat(this[0].name).isEqualTo("Keira High School")
            assertThat(this[0].websiteUrl).isEqualTo("""https://keira-h.schools.nsw.gov.au""")
            assertThat(this[0].educationLevel).isEqualTo("Secondary")
            assertThat(this[0].gender).isEqualTo("Co-ed")
            assertThat(this[0].system).isEqualTo("Government")

            assertThat(this[1].id).isEqualTo(42848)
            assertThat(this[1].acaraId).isEqualTo(42848)
            assertThat(this[1].domainId).isEqualTo(9892)
            assertThat(this[1].address).isEqualTo("Wollongong NSW 2500")
            assertThat(this[1].yearRange).isEqualTo("K-6")
            assertThat(this[1].name).isEqualTo("Wollongong Public School")
            assertThat(this[1].websiteUrl).isEqualTo("""http://www.wollongong-p.schools.nsw.edu.au""")
            assertThat(this[1].educationLevel).isEqualTo("Primary")
            assertThat(this[1].gender).isEqualTo("Co-ed")
            assertThat(this[1].system).isEqualTo("Government")
        }

        with(data.inspectionSchedule) {
            assertThat(isByAppointmentOnly).isFalse()
            assertThat(isBookingsEnabled).isFalse()
            assertThat(inspections.count()).isEqualTo(1)
            assertThat(inspections[0].recurrenceType).isEqualTo("none")
            assertThat(inspections[0].timeOpen).isEqualTo("2024-05-25T10:00:00")
            assertThat(inspections[0].timeClose).isEqualTo("2024-05-25T12:00:00")
        }

        assertThat(data.childListings.count()).isEqualTo(2)
        with(data.childListings) {
            assertThat(this[0].id).isEqualTo(2019256252)
            assertThat(this[0].bedroomCount).isEqualTo(2.0)
            assertThat(this[0].bathroomCount).isEqualTo(2.0)
            assertThat(this[0].carspaceCount).isEqualTo(1.0)
            assertThat(this[0].price).isEqualTo("Starting from $2,000,000")
            assertThat(this[0].propertyTypeDescription).isEqualTo("newApartments")
            assertThat(this[0].propertyUrl).isEqualTo("""https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256252""")
            assertThat(this[0].propertyImage).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/2019256252_1_1_240521_034448-w3000-h1875""")
            assertThat(this[0].lifecycleStatus).isEqualTo("New Home")

            assertThat(this[1].id).isEqualTo(2019256302)
            assertThat(this[1].bedroomCount).isEqualTo(3.0)
            assertThat(this[1].bathroomCount).isEqualTo(2.0)
            assertThat(this[1].carspaceCount).isEqualTo(1.0)
            assertThat(this[1].price).isEqualTo("Starting from $3,250,000")
            assertThat(this[1].propertyTypeDescription).isEqualTo("newApartments")
            assertThat(this[1].propertyUrl).isEqualTo("""https://www.domain.com.au/13-crown-street-wollongong-nsw-2500-2019256302""")
            assertThat(this[1].propertyImage).isEqualTo("""https://bucket-api.domain.com.au/v1/bucket/image/2019256252_5_1_240521_034450-w2500-h1468""")
            assertThat(this[1].lifecycleStatus).isEqualTo("New Home")
        }


    }

    private fun readFile(): String {
        return ClassLoader.getSystemResourceAsStream("raw/project_details.json")?.let { InputStreamReader(it, "UTF-8").readText() }!!
    }
}