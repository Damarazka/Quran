package com.damarazka.quran.presentation.Quran

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.damarazka.quran.R
import com.damarazka.quran.adapter.SurahAdapter
import com.damarazka.quran.core.data.Resource
import com.damarazka.quran.databinding.ActivityDetailSurahBinding
import com.damarazka.quran.databinding.CustomViewAlertDialogBinding
import com.damarazka.quran.core.domain.model.Ayah
import com.damarazka.quran.core.domain.model.Surah
import com.damarazka.quran.core.network.quran.AyahsItem
import com.damarazka.quran.core.network.quran.SurahItem
import com.damarazka.quran.presentation.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class DetailSurahActivity : AppCompatActivity() {
    private var _binding: ActivityDetailSurahBinding? = null
    private val binding get() = _binding as ActivityDetailSurahBinding

    private var _surah: Surah? = null
    private val surah get() = _surah as Surah

    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer get() = _mediaPlayer as MediaPlayer

    private val quranViewModel: QuranViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _surah = intent.getParcelableExtra(EXTRA_DATA, Surah::class.java)

        initView()
        val mAdapter = SurahAdapter()
        mAdapter.setOnItemClicked(object : SurahAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: Ayah) {
                showAlertDialog(data)
            }

        })

        val number = surah.number
        if (number != null) {
            quranViewModel.getDetailSurahWithQuranEdition(number).observe(this) {
                when (it) {
                    is Resource.Loading -> showLoading(true)

                    is Resource.Success -> {
                        mAdapter.setData(it.data?.get(0)?.ayahs, it.data)
                        binding.rvSurah.layoutManager =
                            LinearLayoutManager(this@DetailSurahActivity)
                        binding.rvSurah.adapter = mAdapter
                        showLoading(false)
                    }

                    is Resource.Error -> {
                        Snackbar.make(binding.root, "Error: " + it.message, Snackbar.LENGTH_INDEFINITE).show()
                        showLoading(false)
                    }
                }
            }
        } else {
            Toast.makeText(this, "Surah Number Not Found. ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                rvSurah.visibility = View.GONE
                cvDetailSurah.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvSurah.visibility = View.VISIBLE
                cvDetailSurah.visibility = View.VISIBLE
            }
        }
    }


    private fun showAlertDialog(dataAudio: Ayah) {
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        view.btnCancel.setOnClickListener {
            mediaPlayer.stop()
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        mediaPlayer.setOnCompletionListener {
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