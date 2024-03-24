package au.com.deanpike.mvi_example.ui.di

import au.com.deanpike.mvi_example.ui.util.dispatcher.DispatcherProvider
import au.com.deanpike.mvi_example.ui.util.dispatcher.DispatcherProviderImpl
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