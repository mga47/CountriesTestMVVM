package am.gar.countrylisttest.ui.fragment

import am.gar.countrylisttest.R
import am.gar.countrylisttest.data.model.Country
import am.gar.countrylisttest.databinding.FragmentCountryDetailBinding
import am.gar.countrylisttest.util.SVGUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Garik on 12-Jan-23, 10:09
 */
@AndroidEntryPoint
class FragmentCountryDetail : Fragment(R.layout.fragment_country_detail) {
    companion object {
        const val TAG = "FragmentCountryDetails"
    }
    
    private var _binding: FragmentCountryDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mSelectedCountry: Country
    
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    
    private fun initViews() {
        mSelectedCountry = FragmentCountryDetailArgs.fromBundle(requireArguments()).selectedCountry
        if (mSelectedCountry.flag != null) {
            SVGUtil.fetchSvg(requireContext(), mSelectedCountry.flag!!, binding.flag)
        }
        binding.name.text = mSelectedCountry.name
        binding.capital.text = mSelectedCountry.capital
        binding.region.text = mSelectedCountry.region
        
        if (mSelectedCountry.timezones != null) {
            var timezones = ""
            for (timezone in mSelectedCountry.timezones!!) {
                timezones += "$timezone,"
            }
            if (timezones.isNotEmpty()) {
                timezones = timezones.removeSuffix(",")
            }
    
            binding.timezones.text = timezones
        }
    
        if (mSelectedCountry.currencies != null) {
            var currencies = ""
            for (currency in mSelectedCountry.currencies!!) {
                currencies += "${currency?.name} ${currency?.code} ${currency?.symbol} \n"
            }
            if (currencies.isNotEmpty()) {
                currencies = currencies.removePrefix("\n")
            }
    
            binding.currency.text = currencies
        }
    }
}