package au.com.deanpike.ui.di

import au.com.deanpike.data.di.NetworkModule
import au.com.deanpike.data.util.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "http://localhost:8080/"
    }
}