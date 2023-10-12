package com.rulyadhika.tanamanio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OwnedPlantActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvPlantList:RecyclerView
    private lateinit var btnBack: Button
    private var list =ArrayList<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owned_plant)

        rvPlantList = findViewById(R.id.rv_owned_plant_list)
        rvPlantList.setHasFixedSize(true)

        list.addAll(getListPlants())
        showRecyclerList()

        btnBack = findViewById(R.id.btn_back)

        btnBack.setOnClickListener(this)
    }

    private fun getListPlants():ArrayList<Plant>{
        val dataName = resources.getStringArray(R.array.data_name)
        val dataLatinaName = resources.getStringArray(R.array.data_latina_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataCategory = resources.getStringArray(R.array.data_category)
        val dataDifficulty = resources.getStringArray(R.array.data_difficulty)
        val dataPicture = resources.getStringArray(R.array.data_photo)
        val dataHowToCare = resources.getStringArray(R.array.data_how_to_care)

        val listPlant = ArrayList<Plant>()

        for (i in dataName.indices){
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

    private fun showRecyclerList():Unit{
        rvPlantList.layoutManager = LinearLayoutManager(this)
        val plantListAdapter = ListPlantAdapter(list)
        rvPlantList.adapter = plantListAdapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> onBackPressed()
        }
    }
}