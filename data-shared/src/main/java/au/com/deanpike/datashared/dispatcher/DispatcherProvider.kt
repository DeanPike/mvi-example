package au.com.deanpike.datashared.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun getIoDispatcher(): CoroutineDispatcher
    fun getMainDispatcher(): CoroutineDispatcher
}