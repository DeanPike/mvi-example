package au.com.deanpike.listings.data.di

import au.com.deanpike.client.model.listing.response.ListingType
import au.com.deanpike.client.usecase.ListingUseCase
import au.com.deanpike.listings.data.converter.ListingConverterFactory
import au.com.deanpike.listings.data.converter.ListingConverterFactoryImpl
import au.com.deanpike.listings.data.datasource.remote.ListingDataSource
import au.com.deanpike.listings.data.datasource.remote.ListingDataSourceImpl
import au.com.deanpike.listings.data.repository.ListingRepository
import au.com.deanpike.listings.data.repository.ListingRepositoryImpl
import au.com.deanpike.listings.data.usecase.ListingUseCaseImpl
import au.com.deanpike.listings.data.util.ListingTypeDeserialiser
import com.google.gson.Gson
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

    @Binds
    abstract fun bindListingUseCase(useCase: ListingUseCaseImpl): ListingUseCase

    companion object {
        @Provides
        fun provideGson(): Gson = GsonBuilder()
            .registerTypeAdapter(ListingType::class.java, ListingTypeDeserialiser())
            .create()
    }

}