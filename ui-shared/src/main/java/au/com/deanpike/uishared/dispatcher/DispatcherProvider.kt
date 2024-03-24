package au.com.deanpike.uishared.dispatcher

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    fun getIoDispatcher(): CoroutineDispatcher
    fun getMainDispatcher(): CoroutineDispatcher
}

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override fun getIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    override fun getMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}