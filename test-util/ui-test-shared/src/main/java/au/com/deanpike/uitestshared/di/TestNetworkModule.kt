package au.com.deanpike.uitestshared.di

import androidx.test.espresso.idling.CountingIdlingResource
import au.com.deanpike.network.di.NetworkModule
import au.com.deanpike.network.util.BaseUrl
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
        return "https://localhost:8080/"
    }

    @Provides
    fun provideIdlingResource(): CountingIdlingResource {
        return CountingIdlingResource("CountingIdlingResource")
    }
}