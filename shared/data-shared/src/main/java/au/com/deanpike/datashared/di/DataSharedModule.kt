package au.com.deanpike.datashared.di

import au.com.deanpike.datashared.dispatcher.DispatcherProvider
import au.com.deanpike.datashared.dispatcher.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSharedModule {

    @Binds
    abstract fun bindsDispatcherProvider(provider: DispatcherProviderImpl): DispatcherProvider
}