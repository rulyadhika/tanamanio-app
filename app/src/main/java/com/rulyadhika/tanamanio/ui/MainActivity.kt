package com.rulyadhika.tanamanio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rulyadhika.tanamanio.model.PlantCategory
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnSeeAllOwnedPlant: Button
    private lateinit var llFewOwnedPlant: LinearLayout
    private lateinit var llPlantCategory: LinearLayout
    private lateinit var llFewHitzPlants: LinearLayout
    private lateinit var btnAboutMe: Button

    private var listFewOwnedPlants = ArrayList<Plant>()
    private var listPlantsCategory = ArrayList<PlantCategory>()
    private var listFewHitzPlants = ArrayList<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSeeAllOwnedPlant = findViewById(R.id.btn_see_all_owned_plant)
        btnSeeAllOwnedPlant.setOnClickListener(this)

        btnAboutMe = findViewById(R.id.about_page)
        btnAboutMe.setOnClickListener(this)

        llFewOwnedPlant = findViewById(R.id.ll_few_owned_plant)
        listFewOwnedPlants.addAll(getFewOwnedPlants())

        if (listFewOwnedPlants.size > 0) {
            for (item in listFewOwnedPlants) {
                val view = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.row_item_plant_card, llFewOwnedPlant, false)

                val tvPlantName: TextView = view.findViewById(R.id.tv_plant_name)
                val tvPlantType: TextView = view.findViewById(R.id.tv_plant_type)
                val tvPlantDifficulty: TextView = view.findViewById(R.id.tv_plant_difficulty)
                val ivPlantPicture: CircleImageView = view.findViewById(R.id.iv_plant_picture)

                tvPlantName.text = item.plantName
                tvPlantType.text = item.plantCategory
                tvPlantDifficulty.text = item.plantDifficulty

                Glide.with(llFewOwnedPlant)
                    .load(item.plantPicture)
                    .into(ivPlantPicture)

                llFewOwnedPlant.addView(view)

                view.setOnClickListener {
                    val intent = Intent(view.context, DetailPlantActivity::class.java);
                    intent.putExtra(DetailPlantActivity.DETAIL_DATA, item)

                    view.context.startActivity(intent)
                }
            }
        } else {
            val view = LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.simple_text_message_with_picture, llFewOwnedPlant, false)
            val tvSimpleMessage: TextView = view.findViewById(R.id.tv_message)
            val ivPictureMessage: ImageView = view.findViewById(R.id.iv_picture_message)

            tvSimpleMessage.text = getString(R.string.dont_have_any_plants)
            ivPictureMessage.setImageResource(R.drawable.sad_face_plant)

            llFewOwnedPlant.addView(view)
        }

        llPlantCategory = findViewById(R.id.ll_plant_category)
        listPlantsCategory.addAll(getListPlantsCategory())

        if (listPlantsCategory.size > 0) {
            for (item in listPlantsCategory) {
                val view = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.square_plant_category_card, llPlantCategory, false)

                val tvPlantCategory: TextView = view.findViewById(R.id.tv_plant_category)
                val ivPlantCategoryPicture: ImageView =
                    view.findViewById(R.id.iv_plant_category_picture)

                tvPlantCategory.text = item.categoryName
                ivPlantCategoryPicture.setImageResource(item.categoryImage)

                llPlantCategory.addView(view)
            }
        }

        llFewHitzPlants = findViewById(R.id.ll_few_hitz_plants)
        listFewHitzPlants.addAll(getFewHitzPlants())

        if (listFewHitzPlants.size > 0) {
            for (item in listFewHitzPlants) {
                val view = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.rectangle_hitz_plant_card, llFewHitzPlants, false)

                val ivPlantPicture : ImageView = view.findViewById(R.id.iv_plant_picture)
                val tvPlantName : TextView = view.findViewById(R.id.tv_plant_name)
                val tvPlantCategory : TextView = view.findViewById(R.id.tv_plant_category)

                Glide.with(llFewHitzPlants).load(item.plantPicture).into(ivPlantPicture)
                tvPlantName.text = item.plantName
                tvPlantCategory.text = item.plantCategory

                llFewHitzPlants.addView(view)
            }
        }

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
            if (i < 3) {
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

    private fun getListPlantsCategory(): ArrayList<PlantCategory> {
        val dataName = resources.getStringArray(R.array.data_plant_category_name)
        val dataImage = resources.obtainTypedArray(R.array.data_plant_category_image)

        val resultArray = ArrayList<PlantCategory>()

        for (i in dataName.indices) {
            val data = PlantCategory(dataName[i], dataImage.getResourceId(i, -1))
            resultArray.add(data)
        }

        return resultArray
    }

    private fun getFewHitzPlants(): ArrayList<Plant> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataLatinaName = resources.getStringArray(R.array.data_latina_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataCategory = resources.getStringArray(R.array.data_category)
        val dataDifficulty = resources.getStringArray(R.array.data_difficulty)
        val dataPicture = resources.getStringArray(R.array.data_photo)
        val dataHowToCare = resources.getStringArray(R.array.data_how_to_care)

        var list = ArrayList<Plant>()

        for (i in dataName.size - 1 downTo 0) {
            if (list.size < 5) {
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
}