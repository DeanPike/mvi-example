package au.com.deanpike.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
//    @Binds
//    abstract fun providesPersonDataSource(dataSource: PersonDataSourceImpl): PersonDataSource
//
//    @Binds
//    abstract fun providesPersonRepository(repository: PersonRepositoryImpl): PersonRepository
//
//    @Binds
//    abstract fun providesPersonUseCase(useCase: PersonUseCaseImpl): PersonUseCase
}