package am.gar.countrylisttest.ui.vm

import am.gar.countrylisttest.R
import am.gar.countrylisttest.data.model.Country
import am.gar.countrylisttest.data.repo.CountryRepo
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by Garik on 12-Jan-23, 10:33
 */
@HiltViewModel
class CountryViewModel @Inject constructor(
    private val countryRepo: CountryRepo
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val countryList = MutableLiveData<List<Country>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    
    init {
        isLoading.value = true
        getAllCountries()
    }
    
    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }
    
    fun getAllCountries() {
        isLoading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = countryRepo.getAll()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    countryList.postValue(response.body())
                    isLoading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }
    
}