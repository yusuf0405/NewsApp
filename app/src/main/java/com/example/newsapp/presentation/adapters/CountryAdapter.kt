package com.example.newsapp.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.CategoryItemBinding
import com.example.newsapp.domain.models.Country

@SuppressLint("NotifyDataSetChanged")
class CountryAdapter(private val actionListener: ItemOnClickListener) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    var countryList: List<Country> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()

        }

    inner class CountryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.apply {
                categoryTitle.text = country.title
                if (country.isClick) {
                    categoryItem.setCardBackgroundColor(Color.GREEN)
                } else {
                    categoryItem.setCardBackgroundColor(Color.WHITE)
                }

            }
            if (!country.isClick) {
                itemView.setOnClickListener {
                    countryList.forEach {
                        it.isClick = false
                    }
                    country.isClick = true
                    actionListener.countryOnClick(countryType = country.country)
                    notifyDataSetChanged()
                }
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CountryAdapter.CountryViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        val binding = CategoryItemBinding.bind(layoutInflater)
        return CountryViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: CountryAdapter.CountryViewHolder, position: Int) {
        holder.bind(country = countryList[position])
    }

    override fun getItemCount(): Int = countryList.size
}