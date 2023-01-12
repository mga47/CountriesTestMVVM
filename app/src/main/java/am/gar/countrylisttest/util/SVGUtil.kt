package am.gar.countrylisttest.util

import am.gar.countrylisttest.R
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.pixplicity.sharp.Sharp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException


/**
 * Created by Garik on 12-Jan-23, 11:35
 */
object SVGUtil {
    private var httpClient: OkHttpClient? = null
    
    fun fetchSvg(context: Context, url: String, target: ImageView) {
        if (httpClient == null) {
            httpClient = OkHttpClient.Builder()
                .cache(
                    Cache(
                        context.cacheDir,
                        5 * 1024 * 1014
                    )
                )
                .build()
        }
        
        val request: Request = Request.Builder().url(url).build()
        
        httpClient?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                target.setImageResource(
                    R.drawable.no_data
                )
            }
    
            override fun onResponse(call: Call, response: Response) {
                val stream = response.body?.byteStream()
                try {
                    Sharp.loadInputStream(stream).into(target)
                } catch (e: IllegalArgumentException) {
                    MainScope().launch {
                        target.setImageResource(
                            R.drawable.no_data
                        )
                    }
                }
                stream?.close()
            }
        })
    }
}