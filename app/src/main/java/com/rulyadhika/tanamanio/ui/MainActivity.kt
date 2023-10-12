package com.rulyadhika.tanamanio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnSeeAllOwnedPlant: Button
    private lateinit var rvFewOwnedPlant: RecyclerView
    private lateinit var btnAboutMe: Button

    private var listFewOwnedPlants = ArrayList<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFewOwnedPlant = findViewById(R.id.rv_few_owned_plant)

        rvFewOwnedPlant.setHasFixedSize(true)
        listFewOwnedPlants.addAll(getFewOwnedPlants())
        showRecyclerList()

        btnSeeAllOwnedPlant = findViewById(R.id.btn_see_all_owned_plant)
        btnSeeAllOwnedPlant.setOnClickListener(this)

        btnAboutMe = findViewById(R.id.about_page)
        btnAboutMe.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_see_all_owned_plant -> {
                val intent = Intent(this@MainActivity, OwnedPlantActivity::class.java)
                startActivity(intent)
            }

            R.id.about_page -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getFewOwnedPlants(): ArrayList<Plant> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataLatinaName = resources.getStringArray(R.array.data_latina_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataCategory = resources.getStringArray(R.array.data_category)
        val dataDifficulty = resources.getStringArray(R.array.data_difficulty)
        val dataPicture = resources.getStringArray(R.array.data_photo)
        val dataHowToCare = resources.getStringArray(R.array.data_how_to_care)

        var list = ArrayList<Plant>()

        for (i in dataName.indices) {
            if (i < 10) {
                val plant = Plant(
                    dataName[i],
                    dataLatinaName[i],
                    dataDescription[i],
                    dataCategory[i],
                    "${dataDifficulty[i]} Dirawat",
                    dataPicture[i],
                    dataHowToCare[i]
                )

                list.add(plant)
            } else {
                break
            }
        }

        return list
    }

    private fun showRecyclerList() {
        rvFewOwnedPlant.layoutManager = LinearLayoutManager(this)
        val plantListAdapter = ListPlantAdapter(listFewOwnedPlants)
        rvFewOwnedPlant.adapter = plantListAdapter
    }
}