package au.com.deanpike.data.api

import au.com.deanpike.data.model.internal.ListingSearchRequest
import java.io.InputStreamReader
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropertyListingApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: PropertyListingApi
    private lateinit var jsonResponse: String

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PropertyListingApi::class.java)
        jsonResponse = readFile()
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getFeedbackQuestions, returns Success`() = runTest {
        val res = MockResponse()//Make a fake response for our server call
        res.setBody(jsonResponse)//set the body of the fake response as the json string you are expecting as a response
        server.enqueue(res)//add it in the server response queue

        val data = api.getListings(
            contentType = "application/json",
            body = ListingSearchRequest(
                searchMode = "buy",
                dwellingTypes = listOf("Apartment / Unit / Flat")
            )
        )
        server.takeRequest()//let the server take the request

        assertThat(data.resultsReturned).isEqualTo(201)
        assertThat(data.searchResults.size).isEqualTo(3)
        assertThat(data.searchResults[0].listingType).isEqualTo("topspot")
        assertThat(data.searchResults[1].listingType).isEqualTo("project")
        assertThat(data.searchResults[2].listingType).isEqualTo("property")

        println(data)
    }

    private fun readFile(): String {
        return ClassLoader.getSystemResourceAsStream("raw/listing_response.json")?.let { InputStreamReader(it, "UTF-8").readText() }!!
    }
}