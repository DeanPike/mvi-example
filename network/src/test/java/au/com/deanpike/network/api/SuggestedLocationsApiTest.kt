package au.com.deanpike.network.api

import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader

class SuggestedLocationsApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: SuggestedLocationsApi
    private lateinit var jsonResponse: String
    private val gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(SuggestedLocationsApi::class.java)
        jsonResponse = readFile()
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `should get suggested locations`() = runTest {
        val res = MockResponse()
        res.setBody(jsonResponse)
        server.enqueue(res)

        val data = api.getSuggestedLocations(
            contentType = "application/json",
            searchText = "Bondi"
        )
        server.takeRequest()

        assertThat(data.suggestedLocations.size).isEqualTo(3)

    }

    private fun readFile(): String {
        return ClassLoader.getSystemResourceAsStream("raw/suggested_locations.json")
            ?.let { InputStreamReader(it, "UTF-8").readText() }!!
    }

}