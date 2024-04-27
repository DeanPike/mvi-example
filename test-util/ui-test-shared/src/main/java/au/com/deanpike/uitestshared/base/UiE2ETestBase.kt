package au.com.deanpike.uitestshared.base

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.uitestshared.HiltTestActivity
import au.com.deanpike.uitestshared.mockserver.MockWebServerDispatcher
import au.com.deanpike.uitestshared.util.MockServerCertificates
import dagger.hilt.android.testing.HiltAndroidRule
import java.net.InetAddress
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
abstract class UiE2ETestBase : UiTestBase() {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var espressoIdlingResource: IdlingResource
    private lateinit var mockServer: MockWebServer

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Inject
    lateinit var webServerDispatcher: MockWebServerDispatcher

    @Inject
    lateinit var idlingResource: CountingIdlingResource

    @Before
    fun setupWebServer() {
        hiltRule.inject()

        Dispatchers.setMain(dispatcherProvider.getMainDispatcher())

        setupIdlingResource()

        setupMockWebServer()
    }

    // There is a bug with compose and espresso to do with idling resources
    // That is why we are removing the idling resource
    // https://issuetracker.google.com/issues/223815266
    private fun setupIdlingResource() {
        espressoIdlingResource = IdlingRegistry.getInstance().resources.first { it.name == "Compose-Espresso link" }
        IdlingRegistry.getInstance().unregister(espressoIdlingResource)

        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDownWebServer() {
        mockServer.shutdown()

        teardownIdlingResource()

        Dispatchers.resetMain()
    }

    private fun teardownIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource)

        IdlingRegistry.getInstance().register(espressoIdlingResource)
    }

    private fun setupMockWebServer() {
        mockServer = MockWebServer()
        mockServer.useHttps(MockServerCertificates.newServerCertificates.sslSocketFactory(), false)
        mockServer.dispatcher = webServerDispatcher
        mockServer.start(InetAddress.getByName(InetAddress.getLocalHost().canonicalHostName), 8080)
    }
}