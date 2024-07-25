package org.redaksi.data.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.redaksi.data.remote.PillarApi
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(): PillarApi {
        val contentType = MediaType.get("application/json")
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return retrofit2.Retrofit.Builder()
            .baseUrl(PillarApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PillarApi::class.java)
    }
}
