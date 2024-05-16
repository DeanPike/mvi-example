package au.com.deanpike.datashared.dispatcher

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override fun getIoDispatcher() = Dispatchers.IO

    override fun getMainDispatcher() = Dispatchers.Main
}