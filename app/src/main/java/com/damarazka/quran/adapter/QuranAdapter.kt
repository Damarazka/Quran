package com.damarazka.quran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damarazka.quran.databinding.FragmentQuranBinding
import com.damarazka.quran.databinding.ItemSurahBinding
import com.damarazka.quran.network.SurahItem

class QuranAdapter : RecyclerView.Adapter<QuranAdapter.MyViewHolder>() {
    private val listSurah = ArrayList<SurahItem>()

    fun setData(list: List<SurahItem>?) {
        if (list == null) return
        listSurah.clear()
        listSurah.addAll(list)
    }

    class MyViewHolder(val binding: ItemSurahBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemSurahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = listSurah.size

    override fun onBindViewHolder(holder: QuranAdapter.MyViewHolder, position: Int) {
        val data = listSurah[position]
        holder.binding.apply {
            tvNumber.text = data.number.toString()
            tvName.text = data.name
            tvSurah.text = data.englishName
            val revelation = data.revelationType
            val numberOfAyah = data.numberOfAyahs
            val resultOfTextAyah = "$revelation - $numberOfAyah Ayahs"
            tvAyah.text = resultOfTextAyah
        }
    }
}