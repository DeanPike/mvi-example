package au.com.deanpike.navigation.di

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import au.com.deanpike.navigation.keys.SearchScreenKey
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
        return NavBackStack(startDestination())
    }
}

private fun startDestination(): NavKey = SearchScreenKey()

/**
 * Resets the app-wide back stack to the start destination. The back stack is a singleton, so it
 * outlives the activity: without this, reopening the app after leaving it with back (while the
 * process is still alive) would show whatever screen was left in it.
 */
fun NavBackStack<NavKey>.resetToStartDestination() {
    clear()
    add(startDestination())
}