package au.com.deanpike.uitestshared.mockserver

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockWebServerDispatcher {
    class RequestDispatcher constructor(private val responseBody: String) : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return if (request.path == "/v1/search" && request.body.readUtf8() == """{"dwelling_types":["House"],"search_mode":"Buy"}""") {
                MockResponse().setResponseCode(200)
                    .setBody(responseBody)
            } else {
                MockResponse().setResponseCode(400)
            }
        }
    }

    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse()
                .setResponseCode(400)
                .setBody("No internet")
        }

    }
}