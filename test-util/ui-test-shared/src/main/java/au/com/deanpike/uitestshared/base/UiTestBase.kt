package au.com.deanpike.uitestshared.base

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import au.com.deanpike.uitestshared.R
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.test.FakeImageLoaderEngine
import org.junit.Before

abstract class UiTestBase {

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @OptIn(ExperimentalCoilApi::class)
    @Before
    fun setupFakeCoil() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.test_listing_placeholder)
        val engine = FakeImageLoaderEngine.Builder()
            .intercept({ it is String && it.endsWith("jpg") }, drawable!!)
            .build()
        val imageLoader = ImageLoader.Builder(context)
            .components { add(engine) }
            .build()
        Coil.setImageLoader(imageLoader)
    }
}