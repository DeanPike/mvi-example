package au.com.deanpike.uitestshared.util

import androidx.test.espresso.IdlingResource
import kotlin.concurrent.Volatile
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

class OkHttp3IdlingResource private constructor(
    private val name: String,
    private val dispatcher: Dispatcher
) : IdlingResource {

    companion object {
        fun create(name: String, client: OkHttpClient): OkHttp3IdlingResource {
            return OkHttp3IdlingResource(name, client.dispatcher)
        }
    }

    @Volatile
    lateinit var callback: IdlingResource.ResourceCallback

    init {
        dispatcher.idleCallback = Runnable {
            callback.onTransitionToIdle()
        }
    }

    override fun getName() = name

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    override fun isIdleNow(): Boolean {
        return dispatcher.runningCallsCount() == 0
    }
}