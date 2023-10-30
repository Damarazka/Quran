package com.damarazka.quran.presentation.Quran

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.damarazka.quran.R
import com.damarazka.quran.adapter.SurahAdapter
import com.damarazka.quran.databinding.ActivityDetailSurahBinding
import com.damarazka.quran.databinding.CustomViewAlertDialogBinding
import com.damarazka.quran.network.AyahsItem
import com.damarazka.quran.network.SurahItem
import java.lang.Exception

class DetailSurahActivity : AppCompatActivity() {
    private var _binding: ActivityDetailSurahBinding? = null
    private val binding get() = _binding as ActivityDetailSurahBinding

    private var _surah: SurahItem? = null
    private val surah get() = _surah as SurahItem

    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer get() = _mediaPlayer as MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _surah = intent.getParcelableExtra(EXTRA_DATA, SurahItem::class.java)

        initView()
        val mAdapter = SurahAdapter()
        mAdapter.setOnItemClicked(object : SurahAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: AyahsItem) {
                showAlertDialog(data)
            }

        })

        val quranViewModel = ViewModelProvider(this)[QuranViewModel::class.java]
        surah.number?.let { quranViewModel.getListAyahBySurah(it) }
        quranViewModel.listAyah.observe(this) {
            mAdapter.setData(it.quranEdition?.get(0)?.ayahs, it.quranEdition)
            binding.rvSurah.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(this@DetailSurahActivity)
            }
        }
    }

    private fun showAlertDialog(dataAudio: AyahsItem) {
        _mediaPlayer = MediaPlayer()
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val view = CustomViewAlertDialogBinding.inflate(layoutInflater)
        builder.setView(view.root)
        view.apply {
            tvDialogSurah.text = surah.englishName
            tvDialogName.text = surah.name
            val ayahInSurah = dataAudio.numberInSurah
            val resultAyahText = "Ayah $ayahInSurah"
            tvDialogAyah.text = resultAyahText
        }
        view.btnPlay.setOnClickListener {
            it.isEnabled = false
            view.btnPlay.text = getString(R.string.playing_audio)
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            try {
                mediaPlayer.setDataSource(dataAudio.audio)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        view.btnCancel.setOnClickListener {
            mediaPlayer.stop()
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        mediaPlayer.setOnCompletionListener{
            builder.dismiss()
        }
    }

    private fun initView() {
        binding.apply {
            tvDetailSurah.text = surah.englishName
            tvDetailNameTranslation.text = surah.englishNameTranslation
            val revelationSurah = surah.revelationType
            val numberAyahs = surah.numberOfAyahs
            val resultAyah = "$revelationSurah - $numberAyahs"
            tvDetailAyah.text = resultAyah
            tvDetailName.text = surah.name
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}