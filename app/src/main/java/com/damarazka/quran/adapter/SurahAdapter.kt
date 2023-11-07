package com.damarazka.quran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damarazka.quran.databinding.ItemAyahBinding
import com.damarazka.quran.domain.model.Ayah
import com.damarazka.quran.domain.model.QuranEdition
import com.damarazka.quran.network.quran.AyahsItem
import com.damarazka.quran.network.quran.QuranEditionItem

class SurahAdapter : RecyclerView.Adapter<SurahAdapter.MyViewHolder>() {
    private val listAyah = ArrayList<Ayah>()
    private val quranEditionItem = ArrayList<QuranEdition>()
    private var onItemClickCallBack : OnItemClickCallBack?= null

    class MyViewHolder(val binding: ItemAyahBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemAyahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = listAyah.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataAyahs = listAyah[position]
        val quranAudio = quranEditionItem[1].ayahs[position]
        val quranIndonesia = quranEditionItem[2].ayahs[position]
        holder.binding.apply {
            itemNumberAyah.text = dataAyahs.numberInSurah.toString()
            itemAyah.text = dataAyahs.text
            itemTranslation.text = quranIndonesia.text
            this.root.setOnClickListener {
                quranAudio.let { data -> onItemClickCallBack?.onItemClicked(data) }
            }
        }
    }

    fun setData(dataAyahs: List<Ayah>?, dataQuranEditionItem: List<QuranEdition>?) {
        if (dataAyahs == null || dataQuranEditionItem == null) return
        listAyah.clear()
        listAyah.addAll(dataAyahs)
        quranEditionItem.clear()
        quranEditionItem.addAll(dataQuranEditionItem)
    }

    fun setOnItemClicked(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }
    interface OnItemClickCallBack{
        fun onItemClicked(data: Ayah)
    }
}