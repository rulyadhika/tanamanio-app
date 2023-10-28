package com.rulyadhika.tanamanio

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class ListPlantAdapter(
    private val listPlant: ArrayList<Plant>,
    private val setSelectedItem: (List<Long>) -> Unit
) :
    RecyclerView.Adapter<ListPlantAdapter.ListViewHolder>() {

    private var manageListState: Boolean = false
    private var listSelectedData = mutableListOf<Long>()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tv_plant_name)
        val itemCategory: TextView = itemView.findViewById(R.id.tv_plant_type)
        val itemDifficulty: TextView = itemView.findViewById(R.id.tv_plant_difficulty)
        val itemPhoto: ImageView = itemView.findViewById(R.id.iv_plant_picture)

        val clCheckboxSelectedItemWrapper: ConstraintLayout =
            itemView.findViewById(R.id.cl_checkbox_selected_item_wrapper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_plant_card, parent, false)

        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPlant.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, _, name, _, _, category, difficulty, photo, _, isSelected) = listPlant[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.itemPhoto)
        holder.itemName.text = name
        holder.itemCategory.text = category
        holder.itemDifficulty.text = difficulty

        if(manageListState){
            if (isSelected) {
                holder.clCheckboxSelectedItemWrapper.visibility = View.VISIBLE
            } else {
                holder.clCheckboxSelectedItemWrapper.visibility = View.GONE
            }
        }else{
            holder.clCheckboxSelectedItemWrapper.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if (!manageListState) {
                val intent = Intent(holder.itemView.context, DetailPlantActivity::class.java);
                intent.putExtra(DetailPlantActivity.DETAIL_DATA, listPlant[position])

                holder.itemView.context.startActivity(intent)
            } else {
                onItemClick(holder)
            }
        }
    }

    // Implement stable IDs
    override fun getItemId(position: Int): Long {
        return listPlant[position].itemId.toLong()
    }

    private fun onItemClick(holder: ListViewHolder) {
        val position: Int = holder.adapterPosition

        if (getItemId(position) !in listSelectedData) {
            listSelectedData.add(getItemId(position))

            holder.clCheckboxSelectedItemWrapper.visibility = View.VISIBLE
            listPlant[position].isSelected = true
        } else {
            listSelectedData.remove(getItemId(position))

            holder.clCheckboxSelectedItemWrapper.visibility = View.GONE
            listPlant[position].isSelected = false
        }

        setSelectedItem(listSelectedData)

        Log.d("log_itemViewSelectedWhileManageListIsTrue", listSelectedData.toString())
    }

    fun getManageListState():Boolean {
        return manageListState
    }

    fun setManageListState(state:Boolean) {
        manageListState = state

        if (!manageListState) {
           resetSelectedItem()
        }
    }

    fun resetSelectedItem(){
        if (listSelectedData.size > 0) {
            for (position in listSelectedData.indices) {
                listPlant[position].isSelected = false
            }

            listSelectedData.clear()
            setSelectedItem(listSelectedData)
        }
    }
}