package au.com.deanpike.detail.ui.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.detail.ui.navigationprovider.detailEntryBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailNavigationModule {
    @IntoSet
    @Provides
    @Singleton
    fun provideListingsEntryBuilder(backStack: NavBackStack<NavKey>): EntryProviderScope<NavKey>.() -> Unit = {
        detailEntryBuilder(backStack)
    }
}