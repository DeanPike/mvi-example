package au.com.deanpike.detail.data.di

import au.com.deanpike.detail.data.converter.DetailConverterFactory
import au.com.deanpike.detail.data.converter.DetailConverterFactoryImpl
import au.com.deanpike.detail.data.datasource.remote.DetailDataSource
import au.com.deanpike.detail.data.datasource.remote.DetailDataSourceImpl
import au.com.deanpike.detail.data.repository.DetailRepository
import au.com.deanpike.detail.data.repository.DetailRepositoryImpl
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
    abstract fun bindDetailDataSource(impl: DetailDataSourceImpl): DetailDataSource

    @Binds
    abstract fun bindDetailRepository(impl: DetailRepositoryImpl): DetailRepository

}