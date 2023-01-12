package am.gar.countrylisttest.data.repo

import am.gar.countrylisttest.data.api.CountryApi
import javax.inject.Inject

/**
 * Created by Garik on 12-Jan-23, 10:29
 */
class CountryRepo @Inject constructor(private val countryApi: CountryApi) {
    suspend fun getAll() = countryApi.getAll()
}