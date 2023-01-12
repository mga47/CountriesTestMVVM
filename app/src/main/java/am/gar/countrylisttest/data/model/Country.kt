package am.gar.countrylisttest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

/**
 * Created by Garik on 12-Jan-23, 10:22
 */
@Parcelize
data class Country (
    var name: String?,
    var flag: String?,
    var region: String?,
    var capital: String?,
    var timezones: List<String?>?,
    var currencies: List<Currency?>?,
) : Parcelable {
    override fun hashCode(): Int {
        return name?.hashCode() ?: Random(10000000).nextInt()
    }
}