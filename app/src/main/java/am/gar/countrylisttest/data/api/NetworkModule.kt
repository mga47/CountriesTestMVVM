package am.gar.countrylisttest.data.api

import am.gar.countrylisttest.util.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Garik on 12-Jan-23, 10:49
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    var retrofitService: CountryApi? = null
    
    
    @Singleton
    @Provides
    fun getInstance(): CountryApi {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(CountryApi::class.java)
        }
        return retrofitService!!
    }
    
}