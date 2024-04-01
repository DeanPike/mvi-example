package au.com.deanpike.data.di

import au.com.deanpike.data.converter.ListingConverterFactory
import au.com.deanpike.data.converter.ListingConverterFactoryImpl
import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.data.datasource.remote.ListingDataSource
import au.com.deanpike.data.datasource.remote.ListingDataSourceImpl
import au.com.deanpike.data.repository.ListingRepository
import au.com.deanpike.data.repository.ListingRepositoryImpl
import au.com.deanpike.data.util.ListingTypeDeserialiser
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

    @Binds
    abstract fun bindListingRepository(repository: ListingRepositoryImpl): ListingRepository

    @Binds
    abstract fun bindListingConverterFactory(factory: ListingConverterFactoryImpl): ListingConverterFactory

    companion object {
        @Provides
        fun provideGson() = GsonBuilder()
            .registerTypeAdapter(ListingType::class.java, ListingTypeDeserialiser())
            .create()
    }

}