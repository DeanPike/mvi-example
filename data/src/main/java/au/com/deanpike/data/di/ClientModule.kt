package au.com.deanpike.data.di

import au.com.deanpike.data.data.datasource.PersonDataSource
import au.com.deanpike.data.data.datasource.PersonDataSourceImpl
import au.com.deanpike.data.data.repository.PersonRepository
import au.com.deanpike.data.data.repository.PersonRepositoryImpl
import au.com.deanpike.client.usecase.PersonUseCase
import au.com.deanpike.data.usecase.PersonUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ClientModule {
    @Binds
    abstract fun providesPersonDataSource(dataSource: PersonDataSourceImpl): PersonDataSource

    @Binds
    abstract fun providesPersonRepository(repository: PersonRepositoryImpl): PersonRepository

    @Binds
    abstract fun providesPersonUseCase(useCase: PersonUseCaseImpl): PersonUseCase
}