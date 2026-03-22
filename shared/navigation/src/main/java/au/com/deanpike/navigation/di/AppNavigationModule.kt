package au.com.deanpike.navigation.di

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.navigation.keys.ListingScreenKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppNavigationModule {
    @Provides
    @Singleton
    fun provideBackStack(): NavBackStack<NavKey> {
        return NavBackStack(ListingScreenKey)
    }
}