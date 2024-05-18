package au.com.deanpike.commonshared.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object CommonSharedModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .create()
}