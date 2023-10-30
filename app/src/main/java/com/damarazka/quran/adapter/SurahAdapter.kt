package com.damarazka.quran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damarazka.quran.databinding.ItemAyahBinding
import com.damarazka.quran.network.AyahsItem
import com.damarazka.quran.network.QuranEdition

class SurahAdapter : RecyclerView.Adapter<SurahAdapter.MyViewHolder>() {
    private val listAyah = ArrayList<AyahsItem>()
    private val quranEdition = ArrayList<QuranEdition>()
    private var onItemClickCallBack : OnItemClickCallBack?= null

    class MyViewHolder(val binding: ItemAyahBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemAyahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = listAyah.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataAyahs = listAyah[position]
        val quranAudio = quranEdition[1].ayahs?.get(position)
        val quranIndonesia = quranEdition[2].ayahs?.get(position)
        holder.binding.apply {
            itemNumberAyah.text = dataAyahs.numberInSurah.toString()
            itemAyah.text = dataAyahs.text
            itemTranslation.text = quranIndonesia?.text
            this.root.setOnClickListener {
                quranAudio?.let { data -> onItemClickCallBack?.onItemClicked(data) }
            }
        }
    }

    fun setData(dataAyahs: List<AyahsItem>?, dataQuranEdition: List<QuranEdition>?) {
        if (dataAyahs == null || dataQuranEdition == null) return
        listAyah.clear()
        listAyah.addAll(dataAyahs)
        quranEdition.clear()
        quranEdition.addAll(dataQuranEdition)
    }

    fun setOnItemClicked(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }
    interface OnItemClickCallBack{
        fun onItemClicked(data: AyahsItem)
    }
}