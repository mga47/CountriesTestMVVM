package am.gar.countrylisttest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Garik on 12-Jan-23, 12:08
 */
@Parcelize
data class Currency (
    var code: String?,
    var name: String?,
    var symbol: String?
) : Parcelable