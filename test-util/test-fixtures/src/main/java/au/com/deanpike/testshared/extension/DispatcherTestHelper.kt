package au.com.deanpike.testshared.extension

import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import kotlinx.coroutines.test.TestDispatcher

object DispatcherTestHelper {
    fun getTestDispatcher(dispatcher: TestDispatcher): DispatcherProvider {
        return object : DispatcherProvider {
            override fun getIoDispatcher() = dispatcher
            override fun getMainDispatcher() = dispatcher
        }
    }
}