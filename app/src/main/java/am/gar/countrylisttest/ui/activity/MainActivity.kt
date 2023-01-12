package am.gar.countrylisttest.ui.activity

import am.gar.countrylisttest.R
import am.gar.countrylisttest.databinding.ActivityMainBinding
import am.gar.countrylisttest.ui.fragment.FragmentCountriesList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}