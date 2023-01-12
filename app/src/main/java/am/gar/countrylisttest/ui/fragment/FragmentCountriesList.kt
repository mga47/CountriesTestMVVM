package am.gar.countrylisttest.ui.fragment

import am.gar.countrylisttest.R
import am.gar.countrylisttest.data.model.Country
import am.gar.countrylisttest.databinding.FragmentCountriesListBinding
import am.gar.countrylisttest.ui.vm.CountryViewModel
import am.gar.countrylisttest.util.SVGUtil
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Garik on 12-Jan-23, 10:00
 */
@AndroidEntryPoint
class FragmentCountriesList : Fragment(R.layout.fragment_countries_list) {
    companion object {
        const val TAG = "FragmentCountriesList"
    }
    
    private var _binding: FragmentCountriesListBinding? = null
    private val binding get() = _binding!!
    private var mAdapter: AdapterCountries? = null
    private val mCountryViewModel by viewModels<CountryViewModel>()
    
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountriesListBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onResume() {
        super.onResume()
        initViews()
        obtainData()
    }
    
    private fun initViews() {
        binding.updateButton.setOnClickListener {
            mCountryViewModel.getAllCountries()
        }
        mCountryViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.no_data_found), Toast.LENGTH_LONG).show()
        }
        mCountryViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressLayout.visibility = if (it) View.VISIBLE else View.GONE
        }
        mCountryViewModel.countryList.observe(viewLifecycleOwner) {
            setAdapter()
        }
    }
    
    private fun obtainData() {
        mCountryViewModel.getAllCountries()
    }
    
    private fun selectCountry(country: Country) {
        val action =  FragmentCountriesListDirections.selectCountryAction(country)
        binding.body.findNavController().navigate(action)
    }
    
    private fun setAdapter() {
        mAdapter = AdapterCountries(
            requireContext(),
            mCountryViewModel.countryList.value as ArrayList<Country>
        )
        mAdapter?.setListener { country -> selectCountry(country) }
        binding.listView.adapter = mAdapter
        binding.listView.layoutManager = LinearLayoutManager(activity)
    }
    
    internal class AdapterCountries(context: Context, items: ArrayList<Country>) :
        RecyclerView.Adapter<AdapterCountries.ItemViewHolder>() {
        
        private val mContext = context
        private var mItems = items
        private var mListener: CountryClickListener? = null
        
        fun interface CountryClickListener {
            fun onClick(country: Country)
        }
        
        fun setListener(listener: CountryClickListener) {
            mListener = listener
        }
        
        fun setItems(items: ArrayList<Country>) {
            this.mItems = items
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.item_country, parent, false)
            return ItemViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = mItems[position]
            holder.name.text = item.name
            if (item.flag != null) {
                SVGUtil.fetchSvg(mContext, item.flag!!, holder.flag)
            }
            holder.body.setOnClickListener {
                mListener?.onClick(item)
            }
            
            holder.setIsRecyclable(true)
        }
        
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        
        override fun getItemCount(): Int {
            return mItems.size
        }
        
        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var body: LinearLayout = itemView.findViewById(R.id.body)
            var name: TextView = itemView.findViewById(R.id.name)
            var flag: ImageView = itemView.findViewById(R.id.flag)
        }
    }
}