package au.com.deanpike.detail.data.di

import au.com.deanpike.detail.client.usecase.ListingDetailUseCase
import au.com.deanpike.detail.data.converter.DetailConverterFactory
import au.com.deanpike.detail.data.converter.DetailConverterFactoryImpl
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSource
import au.com.deanpike.detail.data.datasource.remote.PropertyDetailDataSourceImpl
import au.com.deanpike.detail.data.repository.PropertyDetailRepository
import au.com.deanpike.detail.data.repository.PropertyDetailRepositoryImpl
import au.com.deanpike.detail.data.usecase.ListingDetailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DetailDataModule {
    @Binds
    abstract fun bindDetailConverterFactory(impl: DetailConverterFactoryImpl): DetailConverterFactory

    @Binds
    abstract fun bindPropertyDetailDataSource(impl: PropertyDetailDataSourceImpl): PropertyDetailDataSource

    @Binds
    abstract fun bindPropertyDetailRepository(impl: PropertyDetailRepositoryImpl): PropertyDetailRepository

    @Binds
    abstract fun bindListingDetailUseCase(impl: ListingDetailUseCaseImpl): ListingDetailUseCase

}