package au.com.deanpike.ui.di

import au.com.deanpike.datashared.di.DataSharedModule
import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.testshared.extension.DispatcherTestHelper
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
        return DispatcherTestHelper.getTestDispatcher(UnconfinedTestDispatcher())
    }
}