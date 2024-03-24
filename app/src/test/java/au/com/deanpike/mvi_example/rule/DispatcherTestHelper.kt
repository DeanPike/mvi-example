package au.com.deanpike.mvi_example.rule

import au.com.deanpike.uishared.dispatcher.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain

object DispatcherTestHelper {
    fun getTestDispatcher(dispatcher: TestDispatcher): au.com.deanpike.uishared.dispatcher.DispatcherProvider {
        return object : au.com.deanpike.uishared.dispatcher.DispatcherProvider {
            override fun getIoDispatcher() = dispatcher

            override fun getMainDispatcher() = dispatcher
        }
    }

    fun resetTestDispatcher() {
        Dispatchers.resetMain()
    }
}