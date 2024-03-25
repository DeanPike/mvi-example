package au.com.deanpike.testshared.extension

import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain

object DispatcherTestHelper {
    fun getTestDispatcher(dispatcher: TestDispatcher): DispatcherProvider {
        return object : DispatcherProvider {
            override fun getIoDispatcher() = dispatcher

            override fun getMainDispatcher() = dispatcher
        }
    }

    fun resetTestDispatcher() {
        Dispatchers.resetMain()
    }
}