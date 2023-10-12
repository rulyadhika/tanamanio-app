package com.rulyadhika.tanamanio

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailPlantActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var ivDetailMainBanner: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvDetailTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvDifficulty: TextView
    private lateinit var tvHowToCare: TextView
    private lateinit var btnBack: Button

    companion object {
        const val DETAIL_DATA = "detail_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_plant)

        ivDetailMainBanner = findViewById(R.id.iv_detail_main_banner)
        tvTitle = findViewById(R.id.tv_title)
        tvDetailTitle = findViewById(R.id.tv_detail_title)
        tvDescription = findViewById(R.id.tv_description)
        tvDifficulty = findViewById(R.id.tv_difficulty)
        btnBack = findViewById(R.id.btn_back)
        tvHowToCare = findViewById(R.id.tv_how_to_care)

        val detailPlant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<Plant>(DETAIL_DATA, Plant::class.java)
        } else {
            @Suppress("DEPRECIATION")
            intent.getParcelableExtra<Plant>(DETAIL_DATA)
        }

        if (detailPlant != null) {
            Glide.with(this@DetailPlantActivity)
                .load(detailPlant.plantPicture)
                .into(ivDetailMainBanner)

            tvTitle.text = detailPlant.plantName
            tvDetailTitle.text = detailPlant.plantLatinaName
            tvDescription.text = detailPlant.plantDescription
            tvDifficulty.text = detailPlant.plantDifficulty
            tvHowToCare.text = detailPlant.plantHowToCare
        }

        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> onBackPressed()
        }
    }
}