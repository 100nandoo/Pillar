package org.redaksi.data.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.redaksi.data.remote.PillarApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(): PillarApi {
        val contentType = MediaType.get("application/json")
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return retrofit2.Retrofit.Builder()
            .baseUrl("https://www.buletinpillar.org/prog/api/app-apis.php/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PillarApi::class.java)
    }
}
