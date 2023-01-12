package am.gar.countrylisttest.data.api

import am.gar.countrylisttest.data.model.Country
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Garik on 12-Jan-23, 10:24
 */
/*
interface CountryApi {
    @GET("all")
    suspend fun getAll(): Response<List<Country>>
}*/

interface CountryApi {
    @GET("all")
    suspend fun getAll(): Response<List<Country>>
}
