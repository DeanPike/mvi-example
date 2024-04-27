package au.com.deanpike.uitestshared.di

import au.com.deanpike.datashared.di.DataSharedModule
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataSharedModule::class]
)
class TestDataSharedModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        val dispatcher = UnconfinedTestDispatcher()

        return object : DispatcherProvider {
            override fun getIoDispatcher() = dispatcher

            override fun getMainDispatcher() = dispatcher
        }
    }
}