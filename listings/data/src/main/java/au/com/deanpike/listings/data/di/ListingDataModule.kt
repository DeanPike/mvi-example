package au.com.deanpike.listings.data.di

import au.com.deanpike.listings.client.usecase.ListingUseCase
import au.com.deanpike.listings.client.usecase.SuggestedLocationsUseCase
import au.com.deanpike.listings.data.converter.ListingConverterFactory
import au.com.deanpike.listings.data.converter.ListingConverterFactoryImpl
import au.com.deanpike.listings.data.converter.SuggestedLocationConverter
import au.com.deanpike.listings.data.converter.SuggestedLocationConverterImpl
import au.com.deanpike.listings.data.datasource.remote.ListingDataSource
import au.com.deanpike.listings.data.datasource.remote.ListingDataSourceImpl
import au.com.deanpike.listings.data.datasource.remote.SuggestedLocationsDataSource
import au.com.deanpike.listings.data.datasource.remote.SuggestedLocationsDataSourceImpl
import au.com.deanpike.listings.data.repository.ListingRepository
import au.com.deanpike.listings.data.repository.ListingRepositoryImpl
import au.com.deanpike.listings.data.repository.SuggestedLocationsRepository
import au.com.deanpike.listings.data.repository.SuggestedLocationsRepositoryImpl
import au.com.deanpike.listings.data.usecase.ListingUseCaseImpl
import au.com.deanpike.listings.data.usecase.SuggestedLocationsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ListingDataModule {

    @Binds
    @Singleton
    abstract fun bindListingDataSource(dataSource: ListingDataSourceImpl): ListingDataSource

    @Binds
    @Singleton
    abstract fun bindSuggestedLocationsDataSource(dataSource: SuggestedLocationsDataSourceImpl): SuggestedLocationsDataSource

    @Binds
    @Singleton
    abstract fun bindListingRepository(repository: ListingRepositoryImpl): ListingRepository

    @Binds
    @Singleton
    abstract fun bindSuggestedLocationsRepository(repository: SuggestedLocationsRepositoryImpl): SuggestedLocationsRepository

    @Binds
    @Singleton
    abstract fun bindListingConverterFactory(factory: ListingConverterFactoryImpl): ListingConverterFactory

    @Binds
    @Singleton
    abstract fun bindSuggestedLocationConverter(converter: SuggestedLocationConverterImpl): SuggestedLocationConverter

    @Binds
    @Singleton
    abstract fun bindListingUseCase(useCase: ListingUseCaseImpl): ListingUseCase

    @Binds
    @Singleton
    abstract fun bindSuggestedLocationsUseCase(useCase: SuggestedLocationsUseCaseImpl): SuggestedLocationsUseCase
}