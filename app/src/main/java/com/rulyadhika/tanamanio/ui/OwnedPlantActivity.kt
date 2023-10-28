package com.rulyadhika.tanamanio

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class OwnedPlantActivity : AppCompatActivity() {
    private lateinit var rvPlantList: RecyclerView
    private var baseList = ArrayList<Plant>()
    private var processedList = ArrayList<Plant>()

    private lateinit var plantListAdapter: ListPlantAdapter

    private lateinit var topAppBar: MaterialToolbar
    private lateinit var topAppBarSearchView: SearchView

    private lateinit var tvSelectedItemCounter: TextView

    private lateinit var btnDelete: MenuItem

    private var listSelectedItem = ArrayList<Long>()

    override fun onBackPressed() {
        if (!topAppBarSearchView.isIconified) {
            //when search bar is not expanded
            topAppBarSearchView.isIconified = true
        } else {
            if (plantListAdapter.getManageListState()) {
                plantListAdapter.setManageListState(false)

                resetViewSelectItem()
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owned_plant)

        rvPlantList = findViewById(R.id.rv_owned_plant_list)
        rvPlantList.setHasFixedSize(true)

        baseList.addAll(getListPlants())
        processedList.addAll(baseList)
        showRecyclerList()

        topAppBar = findViewById(R.id.top_app_bar)
        tvSelectedItemCounter = findViewById(R.id.tv_selected_item_counter)

        topAppBarSearchView = findViewById(R.id.search_view)
        topAppBarSearchView.queryHint = getString(R.string.search_your_plant)

        btnDelete = topAppBar.menu.findItem(R.id.deleteButton)

        topAppBarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission
                Log.d("log_search_field_submitted", "true")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search text change
                Log.d("log_search_field_value_is_changed", "true")

                if (newText != null) {
                    val filteredData = baseList.filter {
                        it.plantName.contains(
                            newText.toString(),
                            ignoreCase = true
                        )
                    } as ArrayList<Plant>

                    processedList.clear()
                    processedList.addAll(filteredData)

                    plantListAdapter.notifyDataSetChanged()
                }

                return true
            }
        })

        topAppBar.setNavigationOnClickListener { onBackPressed() }

        topAppBar.setOnMenuItemClickListener { itemMenu ->
            when (itemMenu?.itemId) {
                R.id.manageButton -> {
                    plantListAdapter.setManageListState(!plantListAdapter.getManageListState())

                    val manageListState = plantListAdapter.getManageListState()
                    Log.d("log_manage_button_state", manageListState.toString())

                    if (manageListState) {
                        tvSelectedItemCounter.visibility = View.VISIBLE
                        tvSelectedItemCounter.text =
                            resources.getString(R.string.selected_item_counter)

                        topAppBar.isTitleCentered = false
                        btnDelete.isVisible = true
                    } else {
                        resetViewSelectItem()
                    }

                    true
                }

                R.id.deleteButton -> {
                    Log.d("log_delete_button_is_clicked", "true")
                    Log.d("log_result_selected_item", listSelectedItem.toString())

                    if (listSelectedItem.size > 0) {
                        for (itemId in listSelectedItem) {
                            processedList.removeIf { it.itemId == itemId.toInt() }
                        }

                        listSelectedItem.clear()
                    }

                    plantListAdapter.resetSelectedItem()

                    tvSelectedItemCounter.text = "Item Dipilih : ${listSelectedItem.size}"

                    plantListAdapter.notifyDataSetChanged()
                    true
                }

                else -> false
            }
        }
    }

    private fun getListPlants(): ArrayList<Plant> {
        val dataId = resources.getStringArray(R.array.data_id)
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
                i,
                dataId[i],
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

    private fun showRecyclerList() {
        rvPlantList.layoutManager = LinearLayoutManager(this)
        plantListAdapter = ListPlantAdapter(processedList) { result -> getSelectedItem(result) }
        rvPlantList.adapter = plantListAdapter
    }

    private fun getSelectedItem(data: List<Long>) {
        Log.d("log_result_selected_data_is_updated", "true")

        listSelectedItem.clear()
        listSelectedItem.addAll(data)

        tvSelectedItemCounter.text = "Item Dipilih : ${listSelectedItem.size}"
    }

    private fun resetViewSelectItem() {
        tvSelectedItemCounter.visibility = View.GONE

        topAppBar.isTitleCentered = true
        btnDelete.isVisible = false
        plantListAdapter.notifyDataSetChanged()
    }
}