package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import au.com.deanpike.uitestshared.HiltTestActivity
import au.com.deanpike.uitestshared.util.OkHttp3IdlingResource
import au.com.deanpike.uitestshared.util.ServerPort
import dagger.hilt.android.testing.HiltAndroidRule
import java.net.InetAddress
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class UiE2ETestBase : UiTestBase() {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var espressoIdlingResource: IdlingResource
    private lateinit var mockWebServerIdlingResource: OkHttp3IdlingResource
    lateinit var mockServer: MockWebServer

    abstract fun setupResponses()

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setupWebServer() {
        setupMockWebServer()
        setupResponses()

        val address = InetAddress.getByName("localhost")
        mockServer.start(address, 8080)
        ServerPort.port = mockServer.port
        ServerPort.host = mockServer.hostName

        hiltRule.inject()

        mockWebServerIdlingResource = OkHttp3IdlingResource.create("okhttp", okHttpClient)
        IdlingRegistry.getInstance().register(mockWebServerIdlingResource)
    }

    // There is a bug with compose and espresso to do with idling resources
    // That is why we are removing the idling resource
    // https://issuetracker.google.com/issues/223815266
    @Before
    fun removeIdlingResource() {
        espressoIdlingResource = IdlingRegistry.getInstance().resources.first { it.name == "Compose-Espresso link" }
        IdlingRegistry.getInstance().unregister(espressoIdlingResource)
    }

    @After
    fun tearDownWebServer() {
        mockServer.shutdown()
    }

    @After
    fun addIdlingResource() {
        IdlingRegistry.getInstance().register(espressoIdlingResource)
        IdlingRegistry.getInstance().unregister(mockWebServerIdlingResource as IdlingResource)
    }

    private fun setupMockWebServer() {
        mockServer = MockWebServer()
        mockServer.useHttps(ServerPort.serverCertificates.sslSocketFactory(), false)
    }
}