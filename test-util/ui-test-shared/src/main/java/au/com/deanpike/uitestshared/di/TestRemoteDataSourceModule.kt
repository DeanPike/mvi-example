package au.com.deanpike.uitestshared.di

import au.com.deanpike.data.di.RemoteDataSourceModule
import au.com.deanpike.data.util.BaseUrl
import au.com.deanpike.network.api.PropertyListingApi
import au.com.deanpike.uitestshared.util.MockServerCertificates
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteDataSourceModule::class]
)
object TestRemoteDataSourceModule {

    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .sslSocketFactory(MockServerCertificates.newClientCertificates.sslSocketFactory(), MockServerCertificates.newClientCertificates.trustManager)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        @BaseUrl baseUrl: String
    ) =
        Retrofit.Builder()
            .baseUrl("https://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    internal fun providePropertyListingsRemoteDataSourceApi(retrofit: Retrofit): PropertyListingApi = retrofit.create(PropertyListingApi::class.java)
}