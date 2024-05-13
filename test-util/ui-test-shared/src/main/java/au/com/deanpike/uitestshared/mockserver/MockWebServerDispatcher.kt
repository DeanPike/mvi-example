package au.com.deanpike.uitestshared.mockserver

import android.content.Context
import androidx.test.espresso.idling.CountingIdlingResource
import au.com.deanpike.uitestshared.util.FileUtil
import java.net.HttpURLConnection
import javax.inject.Inject
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

typealias MockRequestHandler = (request: RecordedRequest) -> MockResponse

class MockWebServerDispatcher @Inject constructor(
    private val idlingResource: CountingIdlingResource
) : Dispatcher() {
    private val simpleResponses = mutableMapOf<Pair<String, String>, MockResponse>()
    private val complexResponses = mutableMapOf<String, MockRequestHandler>()

    fun addResponse(
        context: Context,
        pathPattern: String,
        filename: String,
        httpMethod: HttpMethod = HttpMethod.GET,
        body: String = "",
        status: Int = HttpURLConnection.HTTP_OK
    ) {
        val response = mockResponse(FileUtil.readFile(context, filename), status)
        val responseKey = "${httpMethod.name}/$pathPattern"
        // adding the http method into the key allows for a repeated pathPattern
        // that is used by both GET and POST to behave differently for eg.
        if (simpleResponses[Pair(responseKey, body)] != null) {
            simpleResponses.replace(Pair(responseKey, body), response)
        } else {
            simpleResponses[Pair(responseKey, body)] = response
        }
    }

    fun addResponse(
        pathPattern: String,
        requestHandler: MockRequestHandler,
        httpMethod: HttpMethod = HttpMethod.GET,
    ) {
        val responseKey = "${httpMethod.name}/$pathPattern"
        if (complexResponses[responseKey] != null) {
            complexResponses.replace(responseKey, requestHandler)
        } else {
            complexResponses[responseKey] = requestHandler
        }
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        idlingResource.increment()
        Thread.sleep(200) // provide a small delay to better mimic real life network call across a mobile network
        val responseKey = "${HttpMethod.valueOf(request.method ?: "GET")}${request.path}"

        var response = findComplexResponse(responseKey, request)

        if (response == null) {
            response = findSimpleResponse(responseKey, request.body.readUtf8())
        }

        if (response == null) {
            response = errorResponse(responseKey)
        }
        idlingResource.decrement()
        return response
    }

    private fun findComplexResponse(responseKey: String, request: RecordedRequest): MockResponse? {
        for (pathPattern in complexResponses.keys) {
            if (responseKey.matches(Regex(pathPattern))) {
                val handler = complexResponses[pathPattern]
                if (handler != null) {
                    return handler(request)
                }
            }
        }
        return null
    }

    private fun findSimpleResponse(responseKey: String, body: String): MockResponse? {
        for (pathPattern in simpleResponses.keys) {
            if (responseKey.matches(Regex(pathPattern.first)) && body == pathPattern.second) {
                val response = simpleResponses[pathPattern]
                if (response != null) {
                    return response
                }
            }
        }
        return null
    }

    private fun errorResponse(reason: String): MockResponse {
        return mockResponse("""{"error":"response not found for "$reason"}""", HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    private fun mockResponse(responseBody: String, status: Int = HttpURLConnection.HTTP_OK) =
        MockResponse()
            .setResponseCode(status)
            .setBody(responseBody)
}