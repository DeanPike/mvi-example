package au.com.deanpike.client.di

import au.com.deanpike.client.data.datasource.PersonDataSource
import au.com.deanpike.client.data.datasource.PersonDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ClientModule {
    @Binds
    abstract fun providesPersonDataSource(dataSource: PersonDataSourceImpl): PersonDataSource

}