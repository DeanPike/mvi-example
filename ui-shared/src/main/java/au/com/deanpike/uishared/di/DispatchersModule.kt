package au.com.deanpike.uishared.di

import au.com.deanpike.uishared.dispatcher.DispatcherProvider
import au.com.deanpike.uishared.dispatcher.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {
    @Binds
    abstract fun bindsDispatcherProvider(dispatcherProvider: DispatcherProviderImpl): DispatcherProvider
}