package au.com.deanpike.detail.data.di

import au.com.deanpike.detail.client.usecase.PropertyDetailUseCase
import au.com.deanpike.detail.data.cache.ListingCache
import au.com.deanpike.detail.data.cache.ListingCacheImpl
import au.com.deanpike.detail.data.cache.ListingConverter
import au.com.deanpike.detail.data.cache.ListingConverterImpl
import au.com.deanpike.detail.data.cache.ListingFetcher
import au.com.deanpike.detail.data.cache.ListingFetcherImpl
import au.com.deanpike.detail.data.cache.ListingTruth
import au.com.deanpike.detail.data.cache.ListingTruthImpl
import au.com.deanpike.detail.data.datasource.remote.ProjectDetailDataSource
import au.com.deanpike.detail.data.datasource.remote.ProjectDetailDataSourceImpl
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSource
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSourceImpl
import au.com.deanpike.detail.data.repository.PropertyDetailRepository
import au.com.deanpike.detail.data.repository.PropertyDetailRepositoryImpl
import au.com.deanpike.detail.data.usecase.PropertyDetailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DetailDataModule {

    @Binds
    abstract fun bindPropertyDetailDataSource(impl: PropertyDetailDataSourceImpl): PropertyDetailDataSource

    @Binds
    abstract fun bindPropertyDetailRepository(impl: PropertyDetailRepositoryImpl): PropertyDetailRepository

    @Binds
    abstract fun bindPropertyDetailUseCase(impl: PropertyDetailUseCaseImpl): PropertyDetailUseCase

    @Binds
    abstract fun bindProjectDetailDataSource(impl: ProjectDetailDataSourceImpl): ProjectDetailDataSource

    @Binds
    abstract fun bindListingCache(impl: ListingCacheImpl): ListingCache

    @Binds
    abstract fun bindListingFetcher(impl: ListingFetcherImpl): ListingFetcher

    @Binds
    abstract fun bindListingTruth(impl: ListingTruthImpl): ListingTruth

    @Binds
    abstract fun bindListingConverter(impl: ListingConverterImpl): ListingConverter

}