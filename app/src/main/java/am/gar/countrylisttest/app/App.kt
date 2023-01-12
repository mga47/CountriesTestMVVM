package am.gar.countrylisttest.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Garik on 12-Jan-23, 10:01
 */
@HiltAndroidApp
class App : MultiDexApplication() {
    
    companion object {
        lateinit var applicationContext: Context
        
        private fun setAppContext(context: Context) {
            this.applicationContext = context
        }
    }
    override fun onCreate() {
        super.onCreate()
        setAppContext(applicationContext)
    }
}