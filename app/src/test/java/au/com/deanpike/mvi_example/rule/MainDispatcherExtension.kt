package au.com.deanpike.mvi_example.rule

import au.com.deanpike.mvi_example.ui.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class MainDispatcherExtension(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) : BeforeEachCallback, AfterEachCallback, BeforeAllCallback, ParameterResolver {
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }

    private lateinit var dispatcherProvider: DispatcherProvider

    override fun beforeAll(context: ExtensionContext?) {
        dispatcherProvider = DispatcherTestHelper.getTestDispatcher(testDispatcher)
    }

    fun getTestDispatcherProvider() = dispatcherProvider
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.type == DispatcherProvider::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): DispatcherProvider {
        return dispatcherProvider
    }
}