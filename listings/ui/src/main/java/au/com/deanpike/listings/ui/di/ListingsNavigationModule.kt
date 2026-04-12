package au.com.deanpike.listings.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.listings.ui.navigationprovider.listingEntryBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListingsNavigationModule {
    @IntoSet
    @Provides
    @Singleton
    fun provideListingsEntryBuilder(backStack: NavBackStack<NavKey>): EntryProviderScope<NavKey>.() -> Unit = {
        listingEntryBuilder(backStack)
    }
}