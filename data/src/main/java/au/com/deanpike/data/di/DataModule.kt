package au.com.deanpike.data.di

import au.com.deanpike.data.datasource.remote.ListingDataSource
import au.com.deanpike.data.datasource.remote.ListingDataSourceImpl
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindListingDataSource(dataSource: ListingDataSourceImpl): ListingDataSource

    companion object {
        @Provides
        fun provideGson() = GsonBuilder().create()
    }

}