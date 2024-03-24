package au.com.deanpike.mvi_example.rule

import au.com.deanpike.mvi_example.ui.util.dispatcher.DispatcherProvider
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