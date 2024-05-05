package au.com.deanpike.data.di

import au.com.deanpike.data.util.BaseUrl
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