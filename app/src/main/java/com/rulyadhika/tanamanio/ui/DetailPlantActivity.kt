package com.rulyadhika.tanamanio

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetailPlantActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private lateinit var ivDetailMainBanner: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvDetailTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvDifficulty: TextView
    private lateinit var tvHowToCare: TextView
    private lateinit var topAppBar: Toolbar

    private var isFavorite: Boolean = false
    private var isOwned: Boolean = false

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
        tvHowToCare = findViewById(R.id.tv_how_to_care)
        topAppBar = findViewById(R.id.top_app_bar)

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

            topAppBar.setTitle(detailPlant.plantName)

            isFavorite = false
            isOwned = false

            changeStateIfItemIsOwnedOrFavorite()
        }

        topAppBar.setNavigationOnClickListener { onBackPressed() }
        topAppBar.setOnMenuItemClickListener(this)

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.favorite -> {
                val snackbarText =
                    if (isFavorite) "Tanaman dihapus dari daftar favorit" else "Tanaman ditambahkan ke daftar favorit"

                Snackbar.make(topAppBar, snackbarText, Snackbar.LENGTH_SHORT)
                    .show()


                isFavorite = !isFavorite

                changeStateIfItemIsOwnedOrFavorite()
                true
            }

            R.id.collection -> {
                val snackbarText =
                    if (isOwned) "Tanaman dihapus dari koleksi milikmu" else "Tanaman ditambahkan ke daftar koleksi milikmu"

                Snackbar.make(topAppBar, snackbarText, Snackbar.LENGTH_SHORT)
                    .setAction("Lihat") {
                        val intent = Intent(
                            this@DetailPlantActivity,
                            OwnedPlantActivity::class.java
                        )

                        startActivity(intent)
                    }
                    .show()

                isOwned = !isOwned

                changeStateIfItemIsOwnedOrFavorite()
                true
            }

            else -> false
        }
    }

    private fun changeStateIfItemIsOwnedOrFavorite() {
        val btnFavorite: MenuItem = topAppBar.menu.findItem(R.id.favorite)
        val btnCollection: MenuItem = topAppBar.menu.findItem(R.id.collection)

        if (isFavorite) btnFavorite.setIcon(R.drawable.ic_favourite_filled) else btnFavorite.setIcon(
            R.drawable.ic_favourite_border
        )

        if (isOwned) btnCollection.setIcon(R.drawable.ic_collection_min) else btnCollection.setIcon(
            R.drawable.ic_collection_plus
        )

    }
}