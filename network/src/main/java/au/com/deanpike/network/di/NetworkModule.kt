package au.com.deanpike.network.di

import au.com.deanpike.network.util.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl() = "https://domain-adapter-api.domain.com.au/"
}