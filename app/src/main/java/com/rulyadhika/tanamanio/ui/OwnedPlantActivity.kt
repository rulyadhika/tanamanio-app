package com.rulyadhika.tanamanio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class OwnedPlantActivity : AppCompatActivity() {
    private lateinit var rvPlantList: RecyclerView
    private var list = ArrayList<Plant>()

    private lateinit var topAppBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owned_plant)

        rvPlantList = findViewById(R.id.rv_owned_plant_list)
        rvPlantList.setHasFixedSize(true)

        list.addAll(getListPlants())
        showRecyclerList()

        topAppBar = findViewById(R.id.top_app_bar)
        topAppBar.setNavigationOnClickListener { onBackPressed() }
        topAppBar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu?.itemId) {
                R.id.searchButton -> {
                    Snackbar.make(topAppBar, "You clicked on search button", Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun getListPlants(): ArrayList<Plant> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataLatinaName = resources.getStringArray(R.array.data_latina_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataCategory = resources.getStringArray(R.array.data_category)
        val dataDifficulty = resources.getStringArray(R.array.data_difficulty)
        val dataPicture = resources.getStringArray(R.array.data_photo)
        val dataHowToCare = resources.getStringArray(R.array.data_how_to_care)

        val listPlant = ArrayList<Plant>()

        for (i in dataName.indices) {
            val plant = Plant(
                dataName[i],
                dataLatinaName[i],
                dataDescription[i],
                dataCategory[i],
                "${dataDifficulty[i]} Dirawat",
                dataPicture[i],
                dataHowToCare[i]
            )
            listPlant.add(plant)
        }

        return listPlant
    }

    private fun showRecyclerList(): Unit {
        rvPlantList.layoutManager = LinearLayoutManager(this)
        val plantListAdapter = ListPlantAdapter(list)
        rvPlantList.adapter = plantListAdapter
    }
}